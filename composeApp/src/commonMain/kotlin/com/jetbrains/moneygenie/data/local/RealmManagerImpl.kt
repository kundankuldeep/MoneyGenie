package com.jetbrains.moneygenie.data.local

import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.models.Transaction
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

/**
 * Created by Kundan on 23/10/24
 **/
class RealmManagerImpl : RealmManager {

    private lateinit var realm: Realm

    override fun init() {
        val config = RealmConfiguration.Builder(
            schema = setOf(Recipient::class, Transaction::class) // Add your Realm objects here
        ).name("moneygenie.realm")
            .build()
        realm = Realm.open(config)
    }

    override fun <T : RealmObject> addObject(obj: T) {
        realm.writeBlocking {
            copyToRealm(obj)
        }
    }

    override fun <T : RealmObject> getObjectById(clazz: KClass<T>, id: String): T? {
        val query: RealmQuery<T> = realm.query(clazz, "id = $0", id)
        return query.first().find()
    }

    override fun <T : RealmObject> getAllObjects(clazz: KClass<T>): List<T> {
        val query: RealmQuery<T> = realm.query(clazz)
        val results: RealmResults<T> = query.find()
        return results.toList()
    }

    override fun <T : RealmObject> updateObject(obj: T, update: T.() -> Unit) {
        realm.writeBlocking {
            findLatest(obj)?.apply(update)
        }
    }

    override fun <T : RealmObject> deleteObjectById(clazz: KClass<T>, id: String) {
        realm.writeBlocking {
            val objToDelete: T? = query(clazz, "id = $0", id).first().find()
            if (objToDelete != null) {
                delete(objToDelete)
            }
        }
    }

    override fun closeRealm() {
        if (::realm.isInitialized) {
            realm.close()
        }
    }

    override fun clearDatabase() {
        realm.writeBlocking {
            deleteAll()
        }
    }
}
