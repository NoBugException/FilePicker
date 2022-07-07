package com.yunchong.filepicker

import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import android.view.View

class RecyclerviewItemDividerDeoration(
    private val mType: Int,
    private val mDividerSize: Int,
    private val mColor: Int
) : ItemDecoration() {
    private val mDivider: Drawable
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mColor == -1) {
            //没有设置颜色则使用背景色
        } else {
            val count = parent.childCount
            for (i in 0 until count) {
                val childView = parent.getChildAt(i) ?: continue
                when (mType) {
                    TYPE_HORIZATION -> {
                        val h_left = childView.right
                        val h_right = h_left + mDividerSize
                        val h_Top = childView.top
                        val h_bottom = childView.bottom
                        mDivider.setBounds(h_left, h_Top, h_right, h_bottom)
                        mDivider.draw(c)
                    }
                    TYPE_VERTICAL -> {
                        val v_left = childView.left
                        val v_right = childView.right
                        val v_Top = childView.bottom
                        val v_bottom = v_Top + mDividerSize
                        mDivider.setBounds(v_left, v_Top, v_right, v_bottom)
                        mDivider.draw(c)
                    }
                    TYPE_GRID -> {
                        val g_left = childView.left
                        val g_right = childView.right
                        val g_Top = childView.top
                        val g_bottom = childView.bottom

                        //left
                        mDivider.setBounds(g_left - mDividerSize, g_Top, g_left, g_bottom)
                        mDivider.draw(c)

                        //right
                        var drawable: Drawable = ColorDrawable(mColor)
                        drawable.setBounds(g_right, g_Top, g_right + mDividerSize, g_bottom)
                        drawable.draw(c)
                        //top
                        drawable = ColorDrawable(mColor)
                        drawable.setBounds(
                            g_left - mDividerSize,
                            g_Top - mDividerSize,
                            g_right + mDividerSize,
                            g_Top
                        )
                        drawable.draw(c)
                        //bottom
                        drawable = ColorDrawable(mColor)
                        drawable.setBounds(
                            g_left - mDividerSize,
                            g_bottom,
                            g_right + mDividerSize,
                            g_bottom + mDividerSize
                        )
                        drawable.draw(c)
                    }
                }
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (mType) {
            TYPE_HORIZATION -> outRect[0, 0, mDividerSize] = 0
            TYPE_VERTICAL -> outRect[0, 0, 0] = mDividerSize
            TYPE_GRID -> outRect[mDividerSize, mDividerSize, 0] = 0
        }
    }

    companion object {
        const val TYPE_HORIZATION = 1
        const val TYPE_VERTICAL = 2
        const val TYPE_GRID = 3
    }

    init {
        mDivider = ColorDrawable(mColor)
    }
}