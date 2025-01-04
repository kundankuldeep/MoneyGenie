package com.jetbrains.moneygenie.utils

/**
 * Created by Kundan on 23/10/24
 **/

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object JsonParser {
    val json = Json { ignoreUnknownKeys = true }

    inline fun <reified T> fromJson(jsonString: String): T? {
        return try {
            json.decodeFromString<T>(jsonString)
        } catch (e: SerializationException) {
            null
        }
    }

    inline fun <reified T> toJson(obj: T): String {
        return json.encodeToString(obj)
    }
}
