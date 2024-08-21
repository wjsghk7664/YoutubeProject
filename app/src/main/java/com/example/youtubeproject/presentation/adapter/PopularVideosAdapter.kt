import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.youtubeproject.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoItem(
    val mainImageUrl: String?,
    val profileImageUrl: String?,
    val title: String,
    val description: String
) : Parcelable



class PopularVideosAdapter :
    ListAdapter<VideoItem, PopularVideosAdapter.VideoViewHolder>(VideoItemDiffCallback()) {

    interface ItemClick {
        fun onClick(item: VideoItem)
    }

    var itemClick: ItemClick? = null

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
        val videoItem = getItem(position)

        Glide.with(holder.itemView.context)
            .load(videoItem.profileImageUrl)
            .transform(CircleCrop())
            .into(holder.profileImage)

        Glide.with(holder.itemView.context)
            .load(videoItem.mainImageUrl)
            .transform(RoundedCorners(16))
            .into(holder.mainImage)

        holder.videoDescription.text = videoItem.title

        holder.itemView.setOnClickListener {
            itemClick?.onClick(videoItem)
        }
    }

    class VideoItemDiffCallback : DiffUtil.ItemCallback<VideoItem>() {
        override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
            return oldItem == newItem
        }
    }
}
