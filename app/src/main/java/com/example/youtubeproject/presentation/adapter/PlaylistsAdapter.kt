package com.example.youtubeproject.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.databinding.ItemPlaylistBinding

class PlaylistsAdapter(
    private val onItemClick: (Playlist) -> (Unit),
    private val onLongItemClick: (Playlist) -> Boolean
): ListAdapter<Playlist, PlaylistsAdapter.PlaylistViewHolder>(
    object: DiffUtil.ItemCallback<Playlist>() {
        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
            return oldItem == newItem
        }
    }
) {

    inner class PlaylistViewHolder(private val binding: ItemPlaylistBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Playlist) {
//            GlideApp.with(binding.root)
//                .load(item.imageUrl)
//                .into(binding.image)
            with(binding) {
                playlistTitleTv.text = item.title
                playlistSizeTv.text = item.size.toString()

                root.setOnClickListener {
                    onItemClick(item)
                }
                root.setOnLongClickListener {
                    onLongItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistsAdapter.PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(ItemPlaylistBinding.bind(view))
    }

    override fun onBindViewHolder(holder: PlaylistsAdapter.PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}