package com.nyra.storyapp.point.registrasi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.nyra.storyapp.R
import com.nyra.storyapp.R.string
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.data.remot.autentikasi.BodyAuth
import com.nyra.storyapp.databinding.ActivityRegisterBinding
import com.nyra.storyapp.point.login.LoginActivity
import com.nyra.storyapp.utils.UiValueConst
import com.nyra.storyapp.utils.ext.emailValid
import com.nyra.storyapp.utils.ext.showOkDialog
import com.nyra.storyapp.utils.ext.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val viewModelRegister: RegisterViewModel by viewModels()

    private var _activityRegisterBinding: ActivityRegisterBinding? = null
    private val binding get() = _activityRegisterBinding!!

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_activityRegisterBinding?.root)

        val imageView: ImageView = findViewById(R.id.imgGif)

        Glide.with(this)
            .asGif()
            .load(R.drawable.welcome_gif)
            .into(imageView)

        actionInit()
    }

    private fun actionInit() {
        binding.registerBtn.setOnClickListener {
            val nameUser = binding.editName.text.toString()
            val emailUser = binding.editEmail.text.toString()
            val passUser = binding.editPassword.text.toString()

            Handler(Looper.getMainLooper()).postDelayed({
                when {
                    nameUser.isBlank() -> binding.editName.error = getString(string.error_name_empty)
                    emailUser.isBlank() -> binding.editEmail.error = getString(string.error_email_empty)
                    !emailUser.emailValid() -> binding.editEmail.error = getString(string.error_email_invalid)
                    passUser.isBlank() -> binding.editPassword.error = getString(string.error_password_empty)
                    else -> {
                        val request = BodyAuth(
                            nameUser, emailUser, passUser
                        )
                        userRegister(request)
                    }
                }
            }, UiValueConst.ACTION_DELAYED_TIME)
        }
        binding.tvLoginNow.setOnClickListener {
            LoginActivity.start(this)
        }
    }

    private fun userRegister(userNew: BodyAuth) {
        viewModelRegister.userRegister(userNew).observe(this) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    showLoading(true)
                }
                is ApiResponse.Success -> {
                    try {
                        showLoading(false)
                    } finally {
                        LoginActivity.start(this)
                        finish()
                        showToast(getString(string.message_success_register))
                    }
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    showOkDialog(getString(string.error_dialog_title), response.errorMessage)
                }
                else -> {
                    showToast(getString(string.message_unknown_state))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.editName.isClickable = !isLoading
        binding.editEmail.isClickable = !isLoading
        binding.editPassword.isClickable = !isLoading

        binding.editName.isEnabled = !isLoading
        binding.editEmail.isEnabled = !isLoading
        binding.editPassword.isEnabled = !isLoading

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.backgroundDim.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.registerBtn.isClickable = !isLoading
    }
}