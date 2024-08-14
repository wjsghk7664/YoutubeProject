package com.example.youtubeproject.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R

data class VideoItem(
    val mainImageResId: Int,
    val profileImageResId: Int,
    val description: String
)

class PopularVideosAdapter(private val videoList: List<VideoItem>) :


    RecyclerView.Adapter<PopularVideosAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainImage: ImageView = itemView.findViewById(R.id.main_image)
        val profileImage: ImageView = itemView.findViewById(R.id.profile_image)
        val videoDescription: TextView = itemView.findViewById(R.id.video_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = videoList[position]
        holder.mainImage.setImageResource(videoItem.mainImageResId)
        holder.profileImage.setImageResource(videoItem.profileImageResId)
        holder.videoDescription.text = videoItem.description
    }

    override fun getItemCount(): Int = videoList.size
}
