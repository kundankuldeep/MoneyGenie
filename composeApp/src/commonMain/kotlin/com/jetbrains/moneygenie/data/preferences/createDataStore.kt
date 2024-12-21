package com.jetbrains.moneygenie.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

/**
 * Created by Kundan on 20/12/24
 **/

fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
}

internal const val DATA_STORE_FILE_NAME = "mg_prefs.preferences_pb"