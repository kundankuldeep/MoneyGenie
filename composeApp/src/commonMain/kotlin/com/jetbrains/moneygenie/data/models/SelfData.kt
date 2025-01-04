package com.jetbrains.moneygenie.data.models

import kotlinx.serialization.Serializable

/**
 * Created by Kundan on 01/01/25
 **/

@Serializable
data class SelfData(
    val name: String,
    val email: String,
    val phone: String,
    val dob: String,
    val gender: String
)