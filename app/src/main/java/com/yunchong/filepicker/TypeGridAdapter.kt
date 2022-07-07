package com.yunchong.filepicker

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.yunchong.filepicker.TypeGridAdapter.TypeHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yunchong.filepicker.filedata.TypeModel

/**
 * Created by Branch on 16/7/30.
 */
class TypeGridAdapter(
    private val mContext: Context,
    private val mTypeModelList: List<TypeModel>?
) : RecyclerView.Adapter<TypeHolder>() {

    private var mOnRecyclerItemClickListener: OnRecyclerItemClickListener? = null

    fun getOnRecyclerItemClickListener(): OnRecyclerItemClickListener? {
        return mOnRecyclerItemClickListener
    }

    fun setOnRecyclerItemClickListener(mOnRecyclerItemClickListener: OnRecyclerItemClickListener?) {
        this.mOnRecyclerItemClickListener = mOnRecyclerItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_layout, null, false)
        return TypeHolder(itemView)
    }

    override fun onBindViewHolder(holder: TypeHolder, position: Int) {
        val typeModel = getItem(position)
        if (typeModel != null) {
            Glide.with(mContext)
                .load(typeModel.mTypeIcon)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.mIcon)
            holder.mTitle.text = typeModel.mTitle
            holder.mCount.text = typeModel.mCount.toString()
        }
        holder.itemView.tag = position
    }

    fun getItem(position: Int): TypeModel? {
        return if (position in 0 until itemCount) {
            mTypeModelList?.get(position)
        } else null
    }

    fun getTypeModelList(): List<TypeModel>? {
        return mTypeModelList
    }

    override fun getItemCount(): Int {
        return mTypeModelList?.size ?: 0
    }

    inner class TypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mIcon: ImageView
        val mTitle: TextView
        val mCount: TextView

        init {
            mIcon = itemView.findViewById<View>(R.id.grid_item_icon) as ImageView
            mTitle = itemView.findViewById<View>(R.id.grid_item_title) as TextView
            mCount = itemView.findViewById<View>(R.id.grid_item_count) as TextView
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (mOnRecyclerItemClickListener != null && v.tag != null) {
                mOnRecyclerItemClickListener?.onRecyclerItemClick(v, (v.tag as Int))
            }
        }
    }
}