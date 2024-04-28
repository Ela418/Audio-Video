        package com.example.audiovideo

        import android.Manifest
        import android.content.pm.PackageManager
        import android.hardware.camera2.CameraCharacteristics
        import android.hardware.camera2.CameraManager
        import android.icu.text.SimpleDateFormat
        import android.media.MediaRecorder
        import android.os.Bundle
        import android.os.Environment
        import android.widget.Button
        import android.widget.Toast
        import androidx.appcompat.app.AppCompatActivity
        import androidx.core.app.ActivityCompat
        import androidx.core.content.ContextCompat
        import java.io.File
        import java.io.IOException
        import java.util.Date

        class GrabarVideo : AppCompatActivity() {

            override fun onResume() {
                super.onResume()
                checkCameraResolutions()
            }

            private fun checkCameraResolutions() {
                val manager = getSystemService(CAMERA_SERVICE) as CameraManager
                try {
                    val cameraIds = manager.cameraIdList
                    for (cameraId in cameraIds) {
                        val characteristics = manager.getCameraCharacteristics(cameraId)
                        val streamConfigMap = characteristics.get(
                            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
                        )
                        val sizes = streamConfigMap?.getOutputSizes(MediaRecorder::class.java)
                        sizes?.forEach { size ->
                            println("Camera $cameraId supports video resolution: ${size.width}x${size.height}")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            private var mediaRecorder: MediaRecorder? = null
            private var isRecording = false;
            private var recordNumber = 1

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.grabar_video)

                if (!checkPermissions()) {
                    requestPermissions()
                }

                mediaRecorder = MediaRecorder()

                val recordButton: Button = findViewById(R.id.record_button)
                val stopRecordButton: Button = findViewById(R.id.stop_record_button)

                recordButton.setOnClickListener {
                    if (isRecording) {
                        stopRecording()
                    } else {
                        startRecording()
                    }
                }

                stopRecordButton.setOnClickListener {
                    stopRecording()
                }
            }

            private fun startRecording() {
                val directory = Environment.getExternalStorageDirectory()
                val recordingDir = File(directory, "Recordings")
                if (!recordingDir.exists()) {
                    recordingDir.mkdirs()
                }

                val outputFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString()+ StringBuilder("/")
                    .append(SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(Date()))
                    .append(".mp4")
                    .toString()

                mediaRecorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setVideoSource(MediaRecorder.VideoSource.CAMERA)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                    setVideoFrameRate(10)
                    setVideoSize(1280, 720)
                    setVideoEncoder(MediaRecorder.VideoEncoder.H264)
                    setMaxDuration(50000);
                    setMaxFileSize(5000000);
                    setOutputFile(outputFile);

                    try {
                        prepare()
                        start()
                    }catch (e : IOException)
                    {
                        Toast.makeText(
                            this@GrabarVideo,
                            "Fallo al iniciar la grabación: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }

            private fun stopRecording() {
                try {
                    mediaRecorder?.apply {
                        stop()
                        release()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    mediaRecorder = null
                    isRecording = false
                    recordNumber++
                }
            }

            private fun checkPermissions(): Boolean {
                return (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED)
            }

            private fun requestPermissions() {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                    ),
                    PERMISSIONS_REQUEST_CODE
                )
            }

            companion object {
                private const val PERMISSIONS_REQUEST_CODE = 123
            }


        }