package com.miiikr.taixian.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.VisibleForTesting
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.miiikr.taixian.R
import com.miiikr.taixian.permission.PermissionChecker
import java.lang.RuntimeException

class PermissionActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 0
    lateinit var mChecker: PermissionChecker
    var isRequireCheck = false

    companion object {
        val PERMISSIONS_GRANTED = 0 //权限授权
        val PERMISSIONS_DENIED = 1 //权限拒绝
        val EXTRA_PERMISSIONS = "extra_permission"
        fun startActivityForResult(activity: Activity, requestCode: Int, permission: Array<String>) {
            var intent = Intent()
            intent.setClass(activity, PermissionActivity::class.java)
            intent.putExtra(EXTRA_PERMISSIONS,permission)
            ActivityCompat.startActivityForResult(activity, intent, requestCode, null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent == null || !intent.hasExtra(EXTRA_PERMISSIONS)) {
            throw RuntimeException("PermissionActivity 需要使用静态方法startActivityForResult启动")
        }
        setContentView(R.layout.activity_permission)
        mChecker = PermissionChecker(this)
        isRequireCheck = true
    }

    override fun onResume() {
        super.onResume()
        if (isRequireCheck) {
            val permissions = getPermissions();
            if (mChecker.lacksPermissions(permissions)) {
                requestPermissions(permissions)
            } else {
                allPermissionsGranted()
            }
        } else {
            isRequireCheck = true
        }
    }

    /*
    同意授权所有的权限，返回上一个页面
     */
    fun allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED)
        finish()
    }

    /*
    请求权限
     */
    fun requestPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }

    /*
    获取intent的数据
     */
    fun getPermissions(): Array<String> {
        var list:ArrayList<String> = ArrayList()

        return intent.getStringArrayExtra(EXTRA_PERMISSIONS);
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true
            allPermissionsGranted()
        } else {
            isRequireCheck = false
            showMissingPermissionDialog()
        }
    }

    /*
    查看是否所有的权限同意
     */
    fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grant in grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    fun showMissingPermissionDialog() {
        var builder = AlertDialog.Builder(this@PermissionActivity)
        builder.setTitle("提示")
        builder.setMessage(R.string.string_help_text)

        builder.setNegativeButton("退出", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                setResult(PERMISSIONS_DENIED)
                finish()
            }
        })

        builder.setPositiveButton("设置", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                var intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.setData(Uri.parse("package:" + getPackageName()))
                startActivity(intent)
            }

        })

        builder.show()
    }


}