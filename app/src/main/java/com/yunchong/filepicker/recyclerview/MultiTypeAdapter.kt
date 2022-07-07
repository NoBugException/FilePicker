package com.yunchong.filepicker.recyclerview

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

open class MultiTypeAdapter<T>(
    private val mContext: Context,
    private var mData: List<T>?,
    private val listener: MultiRecycleViewListener<*>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * 设置数据
     * @param mData
     */
    fun setData(mData: List<T>?) {
        this.mData = mData
    }

    override fun getItemViewType(position: Int): Int {
        return listener.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return listener.onCreateViewHolder(parent, viewType)!!
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads != null && !payloads.isEmpty()) {
            listener.onBindViewHolder(holder, position, payloads)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listener.onBindViewHolder(holder, position)
    }
}