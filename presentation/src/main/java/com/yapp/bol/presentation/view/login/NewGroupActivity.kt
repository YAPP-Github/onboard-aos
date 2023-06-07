package com.yapp.bol.presentation.view.login

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.yapp.bol.presentation.databinding.ActivityNewGroupBinding
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.view.login.KakaoTestActivity.Companion.ACCESS_TOKEN
import com.yapp.bol.presentation.viewmodel.NewGroupViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class NewGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGroupBinding
    private val newGroupViewModel: NewGroupViewModel by viewModels()
    private val imageResult= getResultLauncher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val accessToken = intent.getStringExtra(ACCESS_TOKEN) ?: ""
    private fun getResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult
            val imageUri = result.data?.data ?: return@registerForActivityResult
            val imageFile = File(getRealPathFromURI(imageUri))
            newGroupViewModel.updateImageFile(imageFile)
            setGroupImage(imageUri)
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if (buildName == "Xiaomi") return uri.path ?: EMPTY_STRING

        var columnIndex = 0
        val cursor = getCursor(uri, arrayOf(MediaStore.Images.Media.DATA)) ?: return EMPTY_STRING
        if (cursor.moveToFirst()) columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun setGroupImage(imageUri: Uri) {
        Glide.with(this).load(imageUri).fitCenter().into(binding.ivImage)
    }

    private fun getCursor(uri: Uri, proj: Array<String>): Cursor? {
        return contentResolver.query(uri, proj, null, null, null)
    }

    companion object {
        const val REQ_GALLERY = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        const val WRITE_PERMISSION = 1
        const val READ_PERMISSION = 2
    }
}
