package com.jetbrains.moneygenie.data.local

import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

/**
 * Created by Kundan on 23/10/24
 **/
interface RealmManager {
    fun init()
    fun <T : RealmObject> addObject(obj: T)
    fun <T : RealmObject> getObjectById(clazz: KClass<T>, id: String): T?
    fun <T : RealmObject> getAllObjects(clazz: KClass<T>): List<T>
    fun <T : RealmObject> updateObject(obj: T, update: T.() -> Unit)
    fun <T : RealmObject> deleteObjectById(clazz: KClass<T>, id: String)
    fun closeRealm()
    fun clearDatabase()
    fun <T : RealmObject> queryObjects(clazz: KClass<T>, query: String, vararg args: Any?): List<T>
}