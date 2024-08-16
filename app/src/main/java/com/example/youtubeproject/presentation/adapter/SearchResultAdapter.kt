package com.example.youtubeproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.SearchResponse
import com.example.youtubeproject.databinding.ItemSearchResultBinding

class SearchResultAdapter: ListAdapter<SearchResponse, SearchResultAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<SearchResponse>() {
        override fun areItemsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: SearchResponse, newItem: SearchResponse): Boolean = oldItem.id?.videoId == newItem.id?.videoId
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(ItemSearchResultBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSearchResultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResponse) {
            Glide.with(binding.searchResultItemVideoThumbnail.context)
                .load(item.snippet?.thumbnails?.high?.url.toString())
                .error(R.drawable.sample_image)
                .into(binding.searchResultItemVideoThumbnail)

            binding.searchResultItemVideoTitle.text = item.snippet?.title.toString()
            binding.searchResultItemVideoChannelName.text = item.snippet?.channelTitle.toString()
        }
    }
}