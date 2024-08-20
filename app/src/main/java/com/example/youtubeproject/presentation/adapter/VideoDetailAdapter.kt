package com.example.youtubeproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.databinding.ItemPlaylistBinding
import com.example.youtubeproject.databinding.VideoDetailItemBinding

class VideoDetailAdapter(
    private val onItemClick: (VideoModel) -> (Unit),
    private val onLongItemClick: (VideoModel) -> Boolean
): ListAdapter<VideoModel, VideoDetailAdapter.VideoDetailViewHolder>(
    object: DiffUtil.ItemCallback<VideoModel>() {
        override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class VideoDetailViewHolder(private val binding: VideoDetailItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoModel) {
//            GlideApp.with(binding.root)
//                .load(item.thumbnailImg.high.url)
//                .into(binding.thumbnailImg)
            with(binding) {
                videoTitleTv.text = item.snippet.title
                videoOwnerTv.text = item.snippet.channelTitle
                videoDescriptionTv.text = item.snippet.description
                videoPastTv.text = item.snippet.publishedAt

                root.setOnClickListener {
                    onItemClick(item)
                }
                root.setOnLongClickListener {
                    onLongItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_detail_item, parent, false)
        return VideoDetailViewHolder(VideoDetailItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: VideoDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}