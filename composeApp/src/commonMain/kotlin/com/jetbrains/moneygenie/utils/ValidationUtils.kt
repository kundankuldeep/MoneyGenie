package com.jetbrains.moneygenie.utils

/**
 * Created by Kundan on 01/01/25
 **/
object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }
}