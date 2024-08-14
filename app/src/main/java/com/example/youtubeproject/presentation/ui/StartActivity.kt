package com.example.youtubeproject.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.SplashScreen
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.youtubeproject.R
import com.example.youtubeproject.data.model.User
import com.example.youtubeproject.databinding.ActivityStartBinding
import com.example.youtubeproject.presentation.ui.fragment.LoginFragment
import com.example.youtubeproject.presentation.uistate.LoginUiState
import com.example.youtubeproject.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private val viewmodel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()

        lifecycleScope.launch {
            viewmodel.uiState.collectLatest {
                Log.d("로그",it.toString())
                if(it is LoginUiState.Init){
                    viewmodel.autoLogin()
                }
                else if(it is LoginUiState.Success){
                    Toast.makeText(this@StartActivity,"로그인 성공",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@StartActivity, MainActivity::class.java)
                    intent.putExtra("userData",it.user)
                    startActivity(intent)
                }else {
                    supportFragmentManager.beginTransaction().replace(R.id.main, LoginFragment.newInstance()).commit()
                }
            }
        }



    }
}