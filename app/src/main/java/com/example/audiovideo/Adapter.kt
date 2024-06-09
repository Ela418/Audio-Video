package com.example.audiovideo

import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class Adapter {

    class MediaAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val mediaList = mutableListOf<MediaItem>()

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
                    val mediaItem = mediaList[position]
                    audioHolder.title.text = mediaItem.name
                    audioHolder.playButton.setOnClickListener {
                        // Implement your play functionality here
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

        override fun getItemViewType(position: Int): Int {
            return mediaList[position].type
        }

        override fun getItemCount(): Int {
            return mediaList.size
        }

        private fun loadMediaFiles() {
            val audioDir = File(Environment.getExternalStorageDirectory().absolutePath)
            val audioFiles = audioDir.listFiles { _, name -> name.endsWith(".3gp") }
            audioFiles?.forEach {
                mediaList.add(MediaItem(it.name, AUDIO_TYPE, it.absolutePath))
            }

            val videoFiles = audioDir.listFiles { _, name -> name.endsWith(".mp4") }
            videoFiles?.forEach {
                mediaList.add(MediaItem(it.name, VIDEO_TYPE, it.absolutePath))
            }

            notifyDataSetChanged()
        }

        companion object {
            private const val AUDIO_TYPE = 0
            private const val VIDEO_TYPE = 1
        }
    }
}