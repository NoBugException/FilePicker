package com.yunchong.filepicker.filedata

import android.util.ArrayMap
import java.util.ArrayList

/**
 * 文件数据管理类
 */
object FileDataManager {

    private val mAllFileItem = ArrayMap<FileSystemType, MutableList<FileItem>>()

    fun addFileByType(type: FileSystemType?, fileItem: FileItem?) {
        if (type == null || fileItem == null) {
            return
        }
        var fileItemList = mAllFileItem[type]
        if (fileItemList == null) {
            fileItemList = ArrayList()
            mAllFileItem[type] = fileItemList
        }
        fileItemList.add(fileItem)
    }

    fun addFileListByType(type: FileSystemType?, fileItemList: List<FileItem>?) {
        if (type == null || fileItemList == null) {
            return
        }
        var fileItems = mAllFileItem[type]
        if (fileItems == null) {
            fileItems = ArrayList()
            mAllFileItem[type] = fileItems
        }
        fileItems.addAll(fileItemList)
    }

    fun getFileItemListByType(fileSystemType: FileSystemType?): List<FileItem>? {
        return if (fileSystemType == null) {
            null
        } else mAllFileItem[fileSystemType]
    }

    fun getTypeCount(fileSystemType: FileSystemType): Int {
        val fileItemList: List<FileItem>? = mAllFileItem[fileSystemType]
        return fileItemList?.size ?: 0
    }

    fun clearFileData() {
        mAllFileItem.clear()
    }
}