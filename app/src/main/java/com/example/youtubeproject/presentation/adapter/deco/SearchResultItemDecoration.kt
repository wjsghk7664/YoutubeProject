package com.example.youtubeproject.presentation.adapter.deco

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SearchResultItemDecoration(private val spaceSize: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val childAdapterPosition = parent.getChildAdapterPosition(view)
            when (childAdapterPosition) {
                0 -> {
                    bottom = spaceSize
                }
                in 1 until state.itemCount -> {
                    top = spaceSize
                    bottom = spaceSize
                }
                else -> {
                    top = spaceSize
                }
            }
        }
    }
}