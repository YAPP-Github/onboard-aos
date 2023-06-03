package com.yapp.bol.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yapp.bol.presentation.BuildConfig
import com.yapp.bol.presentation.R

class GoogleTestActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val googleLoginClientIntent: Intent by lazy {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_LOGIN_API_KEY)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(this, gso).signInIntent
    }
    private val googleLoginForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            handleGoogleSignInResult(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_test)

        // TEST 용이라 binding 연결해두지 않았습니다.
        findViewById<SignInButton>(R.id.btn_login).setOnClickListener {
            googleLoginForResult.launch(googleLoginClientIntent)
        }
    }

    // 구글 로그인 작업 수행
    private fun handleGoogleSignInResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            result.data?.let { data ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                try {
                    val account = task.getResult(ApiException::class.java)!!
                    startFirebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // TODO : Google Sign In failed, update UI appropriately
                }
            }
        } else {
            // TODO : 그 외 예외 처리 UI 작업
        }
    }

    // 구글 로그인에 성공한 아이디를 Firebase에 통신하여 user 정보 가져오기
    private fun startFirebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                val user = auth.currentUser
                if (task.isSuccessful && user != null) {
                    checkFirebaseIdToken(user)
                } else {
                    // TODO : If sign in fails, display a message to the user.
                }
            }
    }

    // 가져온 user 정보로 idToken 가져오는 작업 수행
    private fun checkFirebaseIdToken(user: FirebaseUser) {
        user.getIdToken(true).addOnSuccessListener {
            Log.d("gso check idToken", it.token ?: "null")
            // TODO 최종 성공 작업 -> login 성공 & 서버로 토큰 보내기 (BOL-11-2 브랜치에서 작업할 예정)
        }
    }
}
