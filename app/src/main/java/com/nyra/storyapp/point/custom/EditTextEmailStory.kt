package com.nyra.storyapp.point.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.nyra.storyapp.R.string
import com.nyra.storyapp.utils.ext.emailValid

class EditTextEmailStory : AppCompatEditText {
    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(x0: CharSequence?, x1: Int, x2: Int, x3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                when {
                    email.isBlank() -> error = context.getString(string.error_email_empty)
                    !email.emailValid() -> error = context.getString(string.error_email_invalid)
                }
            }

            override fun afterTextChanged(x0: Editable?) {
            }

        })
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
}