package com.example.youtubeproject.presentation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R

data class ChannelCategoryItem(
    val imageResId: Int,
    val channelName: String
)

class ChannelCategoryAdapter(private val channelList: List<ChannelCategoryItem>) :
    RecyclerView.Adapter<ChannelCategoryAdapter.ChannelViewHolder>() {

    inner class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.channel_image)
        val textView: TextView = itemView.findViewById(R.id.channel_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel_category, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channelItem = channelList[position]
        holder.imageView.setImageResource(channelItem.imageResId)
        holder.textView.text = channelItem.channelName
    }

    override fun getItemCount(): Int = channelList.size
}
