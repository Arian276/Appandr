package com.bc.tvappvlc.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacingPx: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildAdapterPosition(view)
        val col = pos % spanCount
        if (includeEdge) {
            outRect.left = spacingPx - col * spacingPx / spanCount
            outRect.right = (col + 1) * spacingPx / spanCount
            if (pos < spanCount) outRect.top = spacingPx
            outRect.bottom = spacingPx
        } else {
            outRect.left = col * spacingPx / spanCount
            outRect.right = spacingPx - (col + 1) * spacingPx / spanCount
            if (pos >= spanCount) outRect.top = spacingPx
        }
    }
}