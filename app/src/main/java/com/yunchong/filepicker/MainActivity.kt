package com.yunchong.filepicker

import android.Manifest.permission
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.guolindev.PermissionX
import com.yunchong.filepicker.filedata.FileDataLoader
import com.yunchong.filepicker.filedata.FileDataManager
import com.yunchong.filepicker.filedata.FileSystemType
import com.yunchong.filepicker.filedata.TypeModel
import com.yunchong.filepicker.showfile.ShowFileItemActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), OnRecyclerItemClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTypeGridAdapter: TypeGridAdapter;
    private var mProgressDialog: ProgressDialog? = null

    // 线程池
    private val newCachedThreadPool: ExecutorService = Executors.newSingleThreadExecutor()

    companion object {
        private const val SPAN_COUNT = 3

        private const val ACTIVITY_RESULT = 0x01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        val gridLayoutManager = GridLayoutManager(applicationContext, SPAN_COUNT)
        mRecyclerView.layoutManager = gridLayoutManager
        mRecyclerView.setHasFixedSize(true)
        // 构造列表数据
        val typeModels = ensureGridData()
        mTypeGridAdapter = TypeGridAdapter(applicationContext, typeModels)
        mTypeGridAdapter.setOnRecyclerItemClickListener(this)
        mRecyclerView.adapter = mTypeGridAdapter

        // 请求权限
        requestPermission()
    }

    /**
     * 跳转到权限设置界面
     */
    private fun getAppDetailSettingIntent() {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }

    /**
     * 请求权限
     */
    private fun requestPermission() {
        PermissionX.init(this@MainActivity)
            .permissions(permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    if (checkExternalStorageManager()) {
                        // 加载数据
                        startLoadData()
                    } else {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.data = Uri.parse("package:$packageName")
                        startActivityForResult(intent, ACTIVITY_RESULT)
                    }
                } else {
                    // 跳转到设置页面
                    getAppDetailSettingIntent()
                }
            }
    }

    /**
     * 检查是否强制分区
     *
     * Android 11 （API 30） 之后，执行了强制分区，强制分区之后，使用 MediaStore 只能访问部分文件（图片、音频、视频），
     * 不问访问其他文件，这时需要添加权限 android.permission.MANAGE_EXTERNAL_STORAGE，
     * 但是该权限并不能默认打开，所以需要跳转到设置页面去打开
     */
    private fun checkExternalStorageManager(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) { // 是否有访问所有文件的权限
                return true
            }
        } else {
            return true
        }
        return false
    }

    /**
     * 加载数据
     */
    private fun startLoadData() {
        onStartLoad() // 打开加载对话框
        newCachedThreadPool.execute {
            FileDataLoader.loadFileData(contentResolver)
            runOnUiThread {
                onFinishLoad()
            }
        }
    }

    /**
     * 构造数据
     */
    private fun ensureGridData(): List<TypeModel> {
        val typeModels: MutableList<TypeModel> = ArrayList()
        var typeModel = TypeModel(
            FileSystemType.PHOTO,
            R.mipmap.filesystem_grid_icon_photo,
            getString(R.string.filesystem_grid_photo),
            0
        )
        typeModels.add(typeModel)
        typeModel = TypeModel(
            FileSystemType.MUSIC,
            R.mipmap.filesystem_grid_icon_music,
            getString(R.string.filesystem_grid_music),
            0
        )
        typeModels.add(typeModel)
        typeModel = TypeModel(
            FileSystemType.VIDEO,
            R.mipmap.filesystem_grid_icon_movie,
            getString(R.string.filesystem_grid_video),
            0
        )
        typeModels.add(typeModel)
        typeModel = TypeModel(
            FileSystemType.TEXT,
            R.mipmap.filesystem_grid_icon_text,
            getString(R.string.filesystem_grid_text),
            0
        )
        typeModels.add(typeModel)
        typeModel = TypeModel(
            FileSystemType.ZIP,
            R.mipmap.filesystem_grid_icon_zip,
            getString(R.string.filesystem_grid_zip),
            0
        )
        typeModels.add(typeModel)
        typeModel = TypeModel(
            FileSystemType.BIN,
            R.mipmap.filesystem_grid_icon_bin,
            getString(R.string.filesystem_grid_bin),
            0
        )
        typeModels.add(typeModel)
        return typeModels
    }

    override fun onRecyclerItemClick(itemView: View?, position: Int) {
        val typeModel: TypeModel? = mTypeGridAdapter.getItem(position)
        if (typeModel != null) {
            startActivity(ShowFileItemActivity.newIntent(this, typeModel.mType))
        }
    }

    private fun onStartLoad() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this@MainActivity)
        }
        mProgressDialog?.show()
    }

    private fun onFinishLoad() {
        val typeList: List<TypeModel>? = mTypeGridAdapter.getTypeModelList()
        if (typeList != null) {
            for (typeModel in typeList) {
                typeModel.mCount = FileDataManager.getTypeCount(typeModel.mType)
            }
            mTypeGridAdapter.notifyDataSetChanged()
        }
        mProgressDialog?.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        FileDataManager.clearFileData()
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
        }
    }
}