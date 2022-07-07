package com.yunchong.filepicker.showfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yunchong.filepicker.R
import com.yunchong.filepicker.RecyclerviewItemDividerDeoration
import com.yunchong.filepicker.filedata.FileDataManager
import com.yunchong.filepicker.filedata.FileItem
import com.yunchong.filepicker.filedata.FileSystemType
import com.yunchong.filepicker.filedata.FileSystemType.Companion.getFileTypeByOrdinal
import com.yunchong.filepicker.recyclerview.MultiRecycleViewListener
import com.yunchong.filepicker.recyclerview.MultiTypeAdapter
import com.yunchong.filepicker.showfile.FileHolder.PhotoViewHolder


class ShowFileItemActivity : AppCompatActivity() {

    private var mShowType = FileSystemType.PHOTO
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mShowFileAdapter: MultiTypeAdapter<FileItem>

    companion object {
        private const val SHOW_FILEITEM_TYPE_KEY = "showtype"
        const val SPAN_COUNT = 3
        fun newIntent(context: Context?, type: FileSystemType): Intent {
            val intent = Intent(context, ShowFileItemActivity::class.java)
            intent.putExtra(SHOW_FILEITEM_TYPE_KEY, type.ordinal)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.showfile_recyclerview)
        val fileTypeOrdinal = intent.getIntExtra(SHOW_FILEITEM_TYPE_KEY, FileSystemType.PHOTO.ordinal)
        mShowType = getFileTypeByOrdinal(fileTypeOrdinal)
        initView()
    }

    private fun initView() {
        mRecyclerView = findViewById<View>(R.id.showfile_recyclerview) as RecyclerView
        var layoutManager: RecyclerView.LayoutManager? = null
        var dividerDeoration: RecyclerviewItemDividerDeoration? = null
        when (mShowType) {
            FileSystemType.PHOTO -> {
                layoutManager = GridLayoutManager(applicationContext, SPAN_COUNT)
                dividerDeoration = RecyclerviewItemDividerDeoration(
                    RecyclerviewItemDividerDeoration.TYPE_GRID,
                    resources.getDimensionPixelSize(R.dimen.grid_item_divider_size),
                    -1
                )
            }
            else -> {
                layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                dividerDeoration = RecyclerviewItemDividerDeoration(
                    RecyclerviewItemDividerDeoration.TYPE_VERTICAL,
                    resources.getDimensionPixelSize(R.dimen.vertical_item_divider_size),
                    resources.getColor(R.color.divider_color)
                )
            }
        }
        mRecyclerView.addItemDecoration(dividerDeoration)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
        val fileList: List<FileItem>? = FileDataManager.getFileItemListByType(mShowType)
        mShowFileAdapter = MultiTypeAdapter(this@ShowFileItemActivity, fileList, object :
            MultiRecycleViewListener<FileItem> {

            override fun getItemViewType(position: Int): Int {
                var itemViewType = FileSystemType.PHOTO.ordinal
                if (fileList != null && fileList.isNotEmpty()) {
                    itemViewType = fileList[position].itemViewType
                }
                return itemViewType
            }

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
                var itemView: View? = null
                var holder: RecyclerView.ViewHolder? = null
                when (viewType) {
                    FileSystemType.PHOTO.ordinal -> {
                        itemView = LayoutInflater.from(this@ShowFileItemActivity).inflate(
                            R.layout.showfile_grid_item_layout,
                            parent,
                            false
                        )
                        holder = PhotoViewHolder(itemView)
                    }
                    else -> {
                        itemView = LayoutInflater.from(this@ShowFileItemActivity).inflate(
                            R.layout.showfile_list_item_layout,
                            parent,
                            false
                        )
                        holder = FileHolder.DefaultViewHolder(itemView)
                    }
                }
                return holder
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
                if (holder is PhotoViewHolder) {
                    val photoItem: FileItem? = fileList?.get(position)
                    Glide.with(applicationContext)
                        .load(photoItem?.mFilePath)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.mImgView)
                    holder.mFileName.text = photoItem?.mFileName
                } else {
                    val myHolder: FileHolder.DefaultViewHolder = holder as FileHolder.DefaultViewHolder
                    val otherItem: FileItem? = fileList?.get(position)
                    if (otherItem?.mFileName?.endsWith(".bin") == true) {
                        Glide.with(applicationContext).load(R.mipmap.filesystem_grid_icon_bin).into(holder.mImageView)
                    } else if (otherItem?.mFileName?.endsWith(".txt") == true) {
                        Glide.with(applicationContext).load(R.mipmap.filesystem_grid_icon_text).into(holder.mImageView)
                    } else if (otherItem?.mFileName?.endsWith(".zip") == true) {
                        Glide.with(applicationContext).load(R.mipmap.filesystem_grid_icon_zip).into(holder.mImageView)
                    } else {
                        Glide.with(applicationContext).load(R.mipmap.filesystem_grid_icon_text).into(holder.mImageView)
                    }
                    myHolder.mFilePath.text = otherItem?.mFilePath
                    myHolder.mFileName.text = otherItem?.mFileName
                    myHolder.itemView.setOnClickListener {
                        Toast.makeText(applicationContext, "path:" + otherItem?.mFilePath, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any?>) {
                // payloads 处理
            }
        })
        mRecyclerView.adapter = mShowFileAdapter
    }
}