package com.example.audiovideo

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class Adapter {
    class MediaAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val audioList = mutableListOf<String>()
        private val videoList = mutableListOf<String>()

        init {
            loadMediaFiles()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                AUDIO_TYPE -> {
                    val view = LayoutInflater.from(context).inflate(R.layout.item_audio, parent, false)
                    ViewHolder.AudioViewHolder(view)
                }
                VIDEO_TYPE -> {
                    val view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false)
                    ViewHolder.VideoViewHolder(view)
                }
                else -> throw IllegalArgumentException("Invalid view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                AUDIO_TYPE -> {
                    val audioHolder = holder as ViewHolder.AudioViewHolder
                    val audioFilePath = getAudioFilePath(audioList[position])
                    audioHolder.title.text = "Audio ${position + 1}"
                    audioHolder.playButton.setOnClickListener {
                        // Play audio file using audioFilePath
                        playAudio(audioFilePath)
                    }
                }
                VIDEO_TYPE -> {
                    val videoHolder = holder as ViewHolder.VideoViewHolder
                    val videoFilePath = getVideoFilePath(videoList[position - audioList.size])
                    videoHolder.title.text = "Video ${position - audioList.size + 1}"
                    videoHolder.playButton.setOnClickListener {
                        // Play video file using videoFilePath
                        playVideo(videoFilePath)
                    }
                }
            }
        }

        private fun playAudio(filePath: String) {
            val mediaPlayer = MediaPlayer()
            try {
                mediaPlayer.setDataSource(filePath)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Error playing audio", Toast.LENGTH_SHORT).show()
            }
        }

        private fun playVideo(filePath: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(filePath), "video/mp4")
            context.startActivity(intent)
        }

        /*
         override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType) {
                AUDIO_TYPE -> {
                    val audioHolder = holder as ViewHolder.AudioViewHolder
                    audioHolder.title.text = "Audio ${position + 1}"
                    audioHolder.playButton.setOnClickListener {
                        // Implement your play functionality here
                        playAudio(audioList[position])
                    }
                }
                VIDEO_TYPE -> {
                    val videoHolder = holder as ViewHolder.VideoViewHolder
                    videoHolder.title.text = "Video ${position - audioList.size + 1}"
                    videoHolder.playButton.setOnClickListener {
                        // Implement your play functionality here
                    }
                }
            }
        }
        */

         override fun getItemCount(): Int {
            return audioList.size + videoList.size
        }

         override fun getItemViewType(position: Int): Int {
            return if (position < audioList.size) {
                AUDIO_TYPE
            } else {
                VIDEO_TYPE
            }
        }

        private fun loadMediaFiles() {
            val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val files = downloadDirectory.listFiles { dir, name -> name.endsWith(".mp3") || name.endsWith(".mp4") }
            files?.forEach { file ->
                if (file.name.endsWith(".mp3")) {
                    audioList.add(file.absolutePath)
                } else if (file.name.endsWith(".mp4")) {
                    videoList.add(file.absolutePath)
                }
            }
        }

        private fun getAudioFilePath(filePath: String): String {
            return filePath
        }

        private fun getVideoFilePath(filePath: String): String {
            return filePath
        }

        /*
        private fun loadMediaFiles() {
            val res = context.resources
            val assetManager = res.assets
            val folderList = assetManager.list("raw")
            folderList?.forEach { folderName ->
                val fileList = assetManager.list("raw/$folderName")
                fileList?.forEach { fileName ->
                    val resourceId = res.getIdentifier(fileName, "raw", context.packageName)
                    if (fileName.endsWith(".mp3")) {
                        audioList.add(resourceId)
                    } else if (fileName.endsWith(".mp4")) {
                        videoList.add(resourceId)
                    }
                }
            }
        }
        */

        companion object {
            private const val AUDIO_TYPE = 0
            private const val VIDEO_TYPE = 1
        }
    }
}