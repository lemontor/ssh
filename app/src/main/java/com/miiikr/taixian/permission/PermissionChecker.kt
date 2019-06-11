package com.miiikr.taixian.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat

class PermissionChecker(var context: Context) {

    fun lacksPermissions(permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (p in permissions) {
                if (lackPermission(p)) {
                    return true
                }
            }
        }
        return false
    }

    fun lackPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED
    }

}