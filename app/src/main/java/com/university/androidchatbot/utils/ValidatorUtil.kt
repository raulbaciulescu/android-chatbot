package com.university.androidchatbot.utils

import java.util.regex.Pattern

object ValidatorUtil {
    fun validateEmail(email: String?): Boolean {
        email ?: return false
        val p = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
        val m = p.matcher(email)
        return m.matches()
    }

    fun validatePassword(password: String?): Boolean {
        password ?: return false
        return password.length >= 3
    }

    fun validateName(name: String): Boolean {
        val p = Pattern.compile("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{2,150}")
        val m = p.matcher(name)
        return m.matches()
    }

    fun passwordMatches(firstPass: String, secondPass: String): Boolean {
        return firstPass == secondPass
    }
}