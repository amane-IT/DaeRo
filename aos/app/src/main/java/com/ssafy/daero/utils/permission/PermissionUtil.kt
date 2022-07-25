package com.ssafy.daero.utils.permission

import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

// 권한 체크
fun Fragment.checkPermissions(permissions: List<String>): Boolean {
    permissions.forEach {
        if(ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED)
            return false
    }
    return true
}

// 권한 요청
fun requestPermission(permission: String, granted : () -> Unit, denied : () -> Unit) {
    TedPermission.create()
        .setPermissionListener(object: PermissionListener {
            override fun onPermissionGranted() {
                granted()
            }
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                denied()
            }
        })
        .setDeniedMessage("권한을 허용해주세요.")
        .setPermissions(permission)
        .check()
}

