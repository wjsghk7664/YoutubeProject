package com.example.youtubeproject.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.databinding.ItemMypageLikeBinding

class LikeListAdapter(val onClickOpen:(VideoModel)->Unit, val onClickDelete:(VideoModel)->Unit):
    ListAdapter<VideoModel, ViewHolder>(diffUtil) {

    companion object{
        val diffUtil = object :DiffUtil.ItemCallback<VideoModel>(){
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LikeListViewHolder(ItemMypageLikeBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoModel:VideoModel = currentList[position]
        (holder as LikeListViewHolder).bind(videoModel,LikeList(currentList),onClickOpen,onClickDelete)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class LikeListViewHolder(private val binding: ItemMypageLikeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(videoModel: VideoModel,likeList: LikeList,onClickOpen:(VideoModel)->Unit, onClickDelete:(VideoModel)->Unit) = with(binding){
            Glide.with(binding.root.context)
                .load(videoModel.snippet.thumbnails?.high?.url)
                .into(mypageitemIv)
            mypageitemTv.setText(videoModel.snippet.title)
            root.setOnClickListener {
                onClickOpen(videoModel)
            }
            root.setOnLongClickListener {
                onClickDelete(videoModel)
                return@setOnLongClickListener true
            }
        }
    }



}