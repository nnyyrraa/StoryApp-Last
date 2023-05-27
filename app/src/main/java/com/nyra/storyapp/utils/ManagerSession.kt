package com.nyra.storyapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.nyra.storyapp.utils.ValConst.KEY_EMAIL
import com.nyra.storyapp.utils.ValConst.KEY_LOGIN
import com.nyra.storyapp.utils.ValConst.KEY_TOKEN
import com.nyra.storyapp.utils.ValConst.KEY_USERID
import com.nyra.storyapp.utils.ValConst.KEY_USERNAME
import com.nyra.storyapp.utils.ValConst.PREFS_NAME

class ManagerSession(context: Context) {
    private var preferences: SharedPreferences = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val edit = preferences.edit()

    val getIdUser = preferences.getString(KEY_USERID, "")
    val getUsername = preferences.getString(KEY_USERNAME, "")
    val getEmail = preferences.getString(KEY_EMAIL, "")
    val getToken = preferences.getString(KEY_TOKEN, "")
    val isLogin = preferences.getBoolean(KEY_LOGIN, false)

    fun preferenceSetInt(keyPref: String, value: Int) {
        edit.putInt(keyPref, value)
        edit.apply()
    }

    fun preferenceSetBoolean(keyPref: String, value: Boolean) {
        edit.putBoolean(keyPref, value)
        edit.apply()
    }

    fun preferenceSetString(keyPref: String, value: String) {
        edit.putString(keyPref, value)
        edit.apply()
    }

    fun preferenceClear() {
        edit.clear().apply()
    }

    fun preferenceClearByKey(keyPref: String) {
        edit.remove(keyPref)
        edit.apply()
    }
}