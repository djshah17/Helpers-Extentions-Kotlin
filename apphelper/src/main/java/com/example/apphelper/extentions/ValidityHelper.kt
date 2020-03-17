package com.example.apphelper.extentions

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

fun isStringValid(data: String?): Boolean {
    data?.let {
        return (!TextUtils.isEmpty(it) && !TextUtils.isEmpty(it.trim()))
    }
    return false
}

fun isEmailValid(email: String?): Boolean {
    email?.let {
        return (isStringValid(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }
    return false
}

fun isPasswordValid(password: String?, passwordPattern: String?): Boolean {
    password?.let {
        return (isStringValid(password) && Pattern.compile(passwordPattern).matcher(password).matches())
    }
    return false
}
