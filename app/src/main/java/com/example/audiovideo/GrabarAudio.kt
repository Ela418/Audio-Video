package com.example.audiovideo

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import java.io.IOException
import androidx.appcompat.app.AppCompatActivity

class GrabarAudio {

    var mediaRecorder: MediaRecorder? = null
    var outputFile: String? = null
    var isRecording = false

    companion object {
        private const val REQUEST_PERMISSION_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grabar_audio)

        val startRecordButton = findViewById<Button>(R.id.start_record_button)
        val stopRecordButton = findViewById<Button>(R.id.stop_record_button)
        val customButton = findViewById<Button>(R.id.custom_button)

        startRecordButton.setOnClickListener {
            if (!isRecording) {
                startRecording()
                startRecordButton.isEnabled = false
                stopRecordButton.visibility = Button.VISIBLE
                isRecording = true
            }
        }

        stopRecordButton.setOnClickListener {
            stopRecording()
            startRecordButton.isEnabled = true
            stopRecordButton.visibility = Button.GONE
            isRecording = false
        }

        customButton.setOnClickListener {

        }
    }

    private fun startRecording() {
        outputFile = Environment.getExternalStorageDirectory().absolutePath + "/audio_record.3gp"
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(outputFile)
            try {
                prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            start()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }
}