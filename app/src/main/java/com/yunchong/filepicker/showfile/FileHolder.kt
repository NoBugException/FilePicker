package com.yunchong.filepicker.showfile

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.yunchong.filepicker.R

class FileHolder {

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImgView: ImageView
        var mFileName: TextView

        init {
            mImgView = itemView.findViewById(R.id.showfile_item_icon)
            mFileName = itemView.findViewById(R.id.showfile_item_text)
        }
    }

    class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageView: ImageView
        var mVideoImageView: ImageView
        var mFileName: TextView
        var mFilePath: TextView

        init {
            mImageView = itemView.findViewById(R.id.showfile_list_item_image)
            mVideoImageView = itemView.findViewById(R.id.showfile_list_item_video_image)
            mFileName = itemView.findViewById(R.id.showfile_list_item_title)
            mFilePath = itemView.findViewById(R.id.showfile_list_item_sub)
        }
    }

}