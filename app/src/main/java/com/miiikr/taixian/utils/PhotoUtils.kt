package com.miiikr.taixian.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.Fragment

class PhotoUtils {

    companion object{

        fun takePicture(fragment: Fragment, imageUri: Uri, requestCode: Int) {
            //调用系统相机
            val intentCamera = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            val resInfoList = fragment.activity!!.packageManager
                    .queryIntentActivities(intentCamera, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                fragment.activity!!.grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            intentCamera.action = MediaStore.ACTION_IMAGE_CAPTURE
            //将拍照结果保存至photo_file的Uri中，不保留在相册中
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            fragment.activity!!.startActivityForResult(intentCamera, requestCode)
        }

        fun openPic(fragment: Fragment, requestCode: Int) {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.type = "image/*"
            fragment.startActivityForResult(photoPickerIntent, requestCode)
        }

        fun takePicture(activity: Activity, imageUri: Uri, requestCode: Int) {
            //调用系统相机
            val intentCamera = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            val resInfoList = activity.packageManager
                    .queryIntentActivities(intentCamera, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                activity.grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
            intentCamera.action = MediaStore.ACTION_IMAGE_CAPTURE
            //将拍照结果保存至photo_file的Uri中，不保留在相册中
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            activity.startActivityForResult(intentCamera, requestCode)
        }


        fun openPic(activity: Activity, requestCode: Int) {
            val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoPickerIntent.type = "image/*"
            activity.startActivityForResult(photoPickerIntent, requestCode)
        }


        fun cropImageUri(activity: Activity, orgUri: Uri, desUri: Uri, aspectX: Int, aspectY: Int, width: Int, height: Int, requestCode: Int) {
            val intent = Intent("com.android.camera.action.CROP")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            intent.setDataAndType(orgUri, "image/*")
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX", aspectX)
            intent.putExtra("aspectY", aspectY)
            intent.putExtra("outputX", width)
            intent.putExtra("outputY", height)
            intent.putExtra("scale", true)
            //将剪切的图片保存到目标Uri中
            intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri)
            intent.putExtra("return-data", false)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            intent.putExtra("noFaceDetection", true)
            activity.startActivityForResult(intent, requestCode)
        }

        private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(column_index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }


        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

    }


}