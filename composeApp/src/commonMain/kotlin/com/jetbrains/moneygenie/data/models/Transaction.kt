package com.jetbrains.moneygenie.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock

/**
 * Created by Kundan on 23/10/24
 **/

class Transaction : RealmObject {
    @PrimaryKey
    var id: Long = Clock.System.now().toEpochMilliseconds()

    @Index
    var recipientId: Long = 0L
    var amount: Double = 0.0
    var type: String? = null
    var note: String? = null

    @Index
    var createdDate: Long = Clock.System.now().toEpochMilliseconds()
    var modifiedDate: Long = Clock.System.now().toEpochMilliseconds()
}

