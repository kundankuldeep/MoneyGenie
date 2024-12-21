package com.jetbrains.moneygenie.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * Created by Kundan on 20/12/24
 **/
@OptIn(ExperimentalForeignApi::class)
fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        val dictionary = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(dictionary).path + "/$DATA_STORE_FILE_NAME"
    }
}