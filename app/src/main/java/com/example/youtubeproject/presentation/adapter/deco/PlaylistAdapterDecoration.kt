package com.example.youtubeproject.presentation.adapter.deco

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PlaylistAdapterDecoration: RecyclerView.ItemDecoration() {
    private val horizontalMargin = 5
    private val verticalMargin = 10

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = horizontalMargin

        outRect.set(
            horizontalMargin,
            verticalMargin,
            horizontalMargin,
            verticalMargin
        )
    }
}