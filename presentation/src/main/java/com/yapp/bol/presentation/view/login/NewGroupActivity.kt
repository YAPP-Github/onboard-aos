package com.yapp.bol.presentation.view.login

import android.Manifest
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.yapp.bol.presentation.databinding.ActivityNewGroupBinding
import com.yapp.bol.presentation.view.login.KakaoTestActivity.Companion.ACCESS_TOKEN
import com.yapp.bol.presentation.viewmodel.NewGroupViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class NewGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewGroupBinding
    private val newGroupViewModel: NewGroupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val accessToken = intent.getStringExtra(ACCESS_TOKEN) ?: ""
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
