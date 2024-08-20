package com.example.youtubeproject.presentation.ui

import VideoItem
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.databinding.ItemCategoryBinding
import com.example.youtubeproject.databinding.ItemPlaylistBinding

data class CategoryItem(
    val imageResId: Int,
    val categoryName: String
)

class CategoryAdapter :
    ListAdapter<VideoItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    interface ItemClick {
        fun onClick(item: VideoModel)
    }

    var itemClick : ItemClick? = null

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoItem) {
            Glide.with(binding.root)
                .load(item.mainImageUrl)
                .into(binding.categoryImage)

            binding.categoryName.text = item.title

            binding.root.setOnClickListener {
                //itemClick!!.onClick()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(ItemCategoryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<VideoItem>() {
        override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem == newItem
        }
    }
}
