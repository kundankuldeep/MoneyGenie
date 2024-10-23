package com.jetbrains.moneygenie.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock

/**
 * Created by Kundan on 23/10/24
 **/
class Recipient : RealmObject {
    @PrimaryKey
    var id: Long = Clock.System.now().toEpochMilliseconds()
    var name: String = "" // user input
    var phone: String = "" // user input
    var email: String = "" // user input
    var gender: String = "" // user input
    var note: String? = null // user input
    var createdDate: Long = Clock.System.now().toEpochMilliseconds()
    var updatedDate: Long = Clock.System.now().toEpochMilliseconds()
}