package com.jetbrains.moneygenie.utils

import com.jetbrains.moneygenie.data.models.Recipient
import kotlinx.serialization.json.Json

/**
 * Created by Kundan on 23/10/24
 **/

private fun parseJson(jsonString: String): ArrayList<Recipient> {
    return Json.decodeFromString(jsonString)
}