package com.example.audiovideo

import android.content.Context
<<<<<<< HEAD
=======
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
>>>>>>> 38b8247a0493f63c702bc3ccd87be8da9454b4d8
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
<<<<<<< HEAD
import java.io.File
=======
import java.io.IOException
>>>>>>> 38b8247a0493f63c702bc3ccd87be8da9454b4d8

class Adapter {

    class MediaAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

<<<<<<< HEAD
        private val mediaList = mutableListOf<MediaItem>()
=======
        private val audioList = mutableListOf<String>()
        private val videoList = mutableListOf<String>()
>>>>>>> 38b8247a0493f63c702bc3ccd87be8da9454b4d8

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
<<<<<<< HEAD
=======
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
>>>>>>> 38b8247a0493f63c702bc3ccd87be8da9454b4d8
            when (holder.itemViewType) {
                AUDIO_TYPE -> {
                    val audioHolder = holder as ViewHolder.AudioViewHolder
                    val mediaItem = mediaList[position]
                    audioHolder.title.text = mediaItem.name
                    audioHolder.playButton.setOnClickListener {
                        // Implement your play functionality here
                        playAudio(audioList[position])
                    }
                }
                VIDEO_TYPE -> {
                    val videoHolder = holder as ViewHolder.VideoViewHolder
                    val mediaItem = mediaList[position]
                    videoHolder.title.text = mediaItem.name
                    videoHolder.playButton.setOnClickListener {
                        // Implement your play functionality here
                    }
                }
            }
        }
        */

        override fun getItemViewType(position: Int): Int {
            return mediaList[position].type
        }

        override fun getItemCount(): Int {
            return mediaList.size
        }

        private fun loadMediaFiles() {
<<<<<<< HEAD
            val audioDir = File(Environment.getExternalStorageDirectory().absolutePath)
            val audioFiles = audioDir.listFiles { _, name -> name.endsWith(".3gp") }
            audioFiles?.forEach {
                mediaList.add(MediaItem(it.name, AUDIO_TYPE, it.absolutePath))
=======
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
>>>>>>> 38b8247a0493f63c702bc3ccd87be8da9454b4d8
            }

            val videoFiles = audioDir.listFiles { _, name -> name.endsWith(".mp4") }
            videoFiles?.forEach {
                mediaList.add(MediaItem(it.name, VIDEO_TYPE, it.absolutePath))
            }

            notifyDataSetChanged()
        }
        */

        companion object {
            private const val AUDIO_TYPE = 0
            private const val VIDEO_TYPE = 1
        }
    }
}