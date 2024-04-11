package com.example.audiovideo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter {
    class MediaAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val audioList = mutableListOf<Int>()
        private val videoList = mutableListOf<Int>()

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
                    audioHolder.title.text = "Audio ${position + 1}"
                    audioHolder.playButton.setOnClickListener {
                        // Implement your play functionality here
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

        companion object {
            private const val AUDIO_TYPE = 0
            private const val VIDEO_TYPE = 1
        }
    }
}