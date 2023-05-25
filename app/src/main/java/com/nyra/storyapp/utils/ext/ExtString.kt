package com.nyra.storyapp.utils.ext

import android.text.TextUtils

fun String.timeStamptoString(): String = substring(0, 10)

fun String.emailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}