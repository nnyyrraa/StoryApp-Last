package com.nyra.storyapp.point.splashscreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nyra.storyapp.point.home.MainActivity
import com.nyra.storyapp.R
import com.nyra.storyapp.point.login.LoginActivity
import com.nyra.storyapp.utils.ManagerSession
import com.nyra.storyapp.utils.UiValueConst

@SuppressLint("SplashScreenCustom")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var prefs: ManagerSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        prefs = ManagerSession(this)
        val isLogin = prefs.isLogin
        Handler(Looper.getMainLooper()).postDelayed({
            when {
                isLogin -> {
                    MainActivity.start(this)
                    finish()
                }
                else -> {
                    LoginActivity.start(this)
                    finish()
                }
            }
        }, UiValueConst.LOADING_TIME)

        val imageView: ImageView = findViewById(R.id.imgGif)

        Glide.with(this)
            .asGif()
            .load(R.drawable.welcome_gif)
            .into(imageView)
    }
}