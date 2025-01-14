package com.jetbrains.moneygenie.utils

/**
 * Created by Kundan on 14/01/25
 **/
object MGConstants {
    fun getQuestions(): List<String> {
        return listOf(
            "What are the last four digits of your Driving License?",
            "What is the brand of your first Car?",
            "What is the year of date of birth?",
            "What are the last four digits of your PAN Card?",
            "What are the last four digits of your Aadhaar Card?"
        )
    }
}