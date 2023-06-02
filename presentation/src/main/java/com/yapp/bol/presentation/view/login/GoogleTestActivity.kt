package com.yapp.bol.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
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
    private lateinit var googleLoginClient: GoogleSignInClient
    private lateinit var googleLoginForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_test)

        initGoogleLogin()

        // TEST 용이라 binding 연결해두지 않았습니다.
        findViewById<SignInButton>(R.id.btn_login).setOnClickListener {
            val signInIntent = googleLoginClient.signInIntent
            googleLoginForResult.launch(signInIntent)
        }
    }

    private fun initGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_LOGIN_API_KEY)
            .requestEmail()
            .build()

        googleLoginClient = GoogleSignIn.getClient(this, gso)

        googleLoginForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                handleGoogleSignInResult(result)
            }
    }

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

    private fun startFirebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                val user = auth.currentUser
                if (task.isSuccessful && user != null) {
                    Log.d("gso check uid", user.uid)
                    checkFirebaseIdToken(auth.currentUser)
                } else {
                    // TODO : If sign in fails, display a message to the user.
                }
            }
    }

    private fun checkFirebaseIdToken(user: FirebaseUser?) {
        user?.getIdToken(true)?.addOnSuccessListener {
            Log.d("gso check idToken", it.token ?: "null")
            // TODO 최종 성공 작업
        } ?: kotlin.run {
            // TODO 예외 작업
        }
    }
}
