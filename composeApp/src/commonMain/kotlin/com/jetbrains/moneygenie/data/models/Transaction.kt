package com.jetbrains.moneygenie.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock

/**
 * Created by Kundan on 23/10/24
 **/

class Transaction : RealmObject {
    @PrimaryKey
    var id: Long = Clock.System.now().toEpochMilliseconds()
    var recipientId: String = ""
    var amount: Double = 0.0
    var type: Double = 0.0 // either you owe or they owe
    var note: String? = null
    var date: Long = Clock.System.now().toEpochMilliseconds()
}

