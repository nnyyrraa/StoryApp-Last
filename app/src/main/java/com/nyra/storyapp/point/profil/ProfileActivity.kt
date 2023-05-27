package com.nyra.storyapp.point.profil

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.nyra.storyapp.R.string
import com.nyra.storyapp.databinding.ActivityProfileBinding
import com.nyra.storyapp.point.login.LoginActivity
import com.nyra.storyapp.utils.ManagerSession

class ProfileActivity : AppCompatActivity() {
    private var _activityProfileBinding: ActivityProfileBinding? = null
    private val binding get() = _activityProfileBinding!!

    private lateinit var prefs: ManagerSession

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(_activityProfileBinding?.root)

        prefs = ManagerSession(this)

        uiInit()
        actionInit()
    }

    private fun actionInit() {
        binding.logoutBtn.setOnClickListener {
            logoutOpenDialog()
        }
    }

    private fun logoutOpenDialog() {
        val dialogAlert = AlertDialog.Builder(this)
        dialogAlert.setTitle(getString(string.confirm_logout))?.setPositiveButton(getString(string.logout)) { _, _ ->
            prefs.preferenceClear()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finishAffinity()
        }?.setNegativeButton(getString(string.cancel), null)
        val alerts = dialogAlert.create()
        alerts.show()
    }

    private fun uiInit() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(string.profile)

        binding.tvUsername.text = prefs.getUsername
        binding.tvEmailUser.text = prefs.getEmail
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}