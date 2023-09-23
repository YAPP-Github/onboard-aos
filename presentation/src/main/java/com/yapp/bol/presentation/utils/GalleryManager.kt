package com.yapp.bol.presentation.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class GalleryManager(
    private val context: AppCompatActivity,
    private val imageView: ImageView,
    private val uploadImageFile: (File) -> Unit,
) {

    private val isPermission: Boolean
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getPermission(READ_PERMISSION) != PackageManager.PERMISSION_DENIED
        } else {
            getPermission(WRITE_PERMISSION) != PackageManager.PERMISSION_DENIED &&
                getPermission(READ_PERMISSION) != PackageManager.PERMISSION_DENIED
        }

    private val imageResult = getResultLauncher()

    fun checkedGalleryAccess() {
        if (isPermission) {
            generateGallery()
        } else {
            ActivityCompat.requestPermissions(context, PERMISSIONS, REQ_GALLERY)
        }
    }

    private fun generateGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        }
        imageResult.launch(intent)
    }

    private fun getPermission(permissionType: Int): Int {
        return when (permissionType) {
            WRITE_PERMISSION -> ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            READ_PERMISSION -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                }
                else {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            else -> throw IllegalArgumentException("잘못된 권한 타입입니다.")
        }
    }

    private fun getResultLauncher(): ActivityResultLauncher<Intent> {
        return context.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode != AppCompatActivity.RESULT_OK) return@registerForActivityResult
            val imageUri = result.data?.data ?: return@registerForActivityResult
            val imageFile = File(getRealPathFromURI(imageUri))
            uploadImageFile(imageFile)
            imageView.loadRoundImage(imageUri.toString(), 8)
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if (buildName == "Xiaomi") return uri.path ?: Constant.EMPTY_STRING

        var columnIndex = 0
        val cursor = getCursor(uri, arrayOf(MediaStore.Images.Media.DATA)) ?: return Constant.EMPTY_STRING
        if (cursor.moveToFirst()) columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun getCursor(uri: Uri, proj: Array<String>): Cursor? {
        return context.contentResolver.query(uri, proj, null, null, null)
    }

    companion object {
        const val REQ_GALLERY = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES,
        )
        const val WRITE_PERMISSION = 1
        const val READ_PERMISSION = 2
    }
}
