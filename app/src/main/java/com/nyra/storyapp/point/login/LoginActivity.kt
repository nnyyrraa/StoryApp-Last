package com.nyra.storyapp.point.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.nyra.storyapp.point.home.MainActivity
import com.nyra.storyapp.R
import com.nyra.storyapp.R.string
import com.nyra.storyapp.point.registrasi.RegisterActivity
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.autentikasi.BodyLogin
import com.nyra.storyapp.databinding.ActivityLoginBinding
import com.nyra.storyapp.utils.ManagerSession
import com.nyra.storyapp.utils.ValConst.KEY_EMAIL
import com.nyra.storyapp.utils.ValConst.KEY_LOGIN
import com.nyra.storyapp.utils.ValConst.KEY_TOKEN
import com.nyra.storyapp.utils.ValConst.KEY_USERID
import com.nyra.storyapp.utils.ValConst.KEY_USERNAME
import com.nyra.storyapp.utils.ext.gone
import com.nyra.storyapp.utils.ext.show
import com.nyra.storyapp.utils.ext.showOkDialog
import com.nyra.storyapp.utils.ext.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val viewModelLogin: LoginViewModel by viewModels()

    private var _activityLoginBinding: ActivityLoginBinding? = null
    private val binding get() = _activityLoginBinding!!

    private lateinit var pref: ManagerSession

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_activityLoginBinding?.root)

        pref = ManagerSession(this)

        val imageView: ImageView = findViewById(R.id.imgGif)

        Glide.with(this)
            .asGif()
            .load(R.drawable.welcome_gif)
            .into(imageView)

        actionInit()
    }

    private fun actionInit() {
        binding.loginBtn.setOnClickListener {
            val emailUser = binding.editEmail.text.toString()
            val passUser = binding.editPassword.text.toString()

            when {
                emailUser.isBlank() -> {
                    binding.editEmail.requestFocus()
                    binding.editEmail.error = getString(string.error_password_empty)
                }
                passUser.isBlank() -> {
                    binding.editPassword.requestFocus()
                    binding.editPassword.error = getString(string.error_password_empty)
                }
                else -> {
                    val request = BodyLogin(emailUser, passUser)

                    userLogin(request, emailUser)
                }
            }
        }
        binding.tvRegisterNow.setOnClickListener {
            RegisterActivity.start(this)
        }
    }

    private fun userLogin(bodyLogin: BodyLogin, email: String) {
        viewModelLogin.userLogin(bodyLogin).observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    try {
                        showLoading(false)
                        val dataUser = response.data.loginResult
                        pref.apply {
                            preferenceSetString(KEY_USERID, dataUser.userId)
                            preferenceSetString(KEY_TOKEN,dataUser.token)
                            preferenceSetString(KEY_USERNAME, dataUser.name)
                            preferenceSetString(KEY_EMAIL, email)
                            preferenceSetBoolean(KEY_LOGIN, true)
                        }
                    } finally {
                        MainActivity.start(this)
                        finish()
                    }
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    showOkDialog(getString(string.error_dialog_title), getString(string.incorrect_message))
                }
                else -> {
                    showToast(getString(string.message_unknown_state))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.editEmail.isClickable = !isLoading
        binding.editPassword.isClickable = !isLoading

        binding.editEmail.isEnabled = !isLoading
        binding.editPassword.isEnabled = !isLoading

        binding.loginBtn.isClickable = !isLoading

        if (isLoading) binding.progressBar.show() else binding.progressBar.gone()
        if (isLoading) binding.backgroundDim.show() else binding.backgroundDim.gone()
    }
}