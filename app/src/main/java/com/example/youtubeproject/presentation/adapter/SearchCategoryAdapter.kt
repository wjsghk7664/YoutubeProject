package com.example.youtubeproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R
import com.example.youtubeproject.databinding.ItemSearchCategoryBinding

class SearchCategoryAdapter(private val onClick: (String) -> Unit): ListAdapter<String, SearchCategoryAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_category, parent, false)
        return ViewHolder(ItemSearchCategoryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemSearchCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.categoryItemTitleText.text = title

            binding.root.setOnClickListener {
                onClick(title)
            }
        }
    }
}