package com.jetbrains.moneygenie.di

import com.jetbrains.moneygenie.data.InMemoryMuseumStorage
import com.jetbrains.moneygenie.data.KtorMuseumApi
import com.jetbrains.moneygenie.data.MuseumApi
import com.jetbrains.moneygenie.data.MuseumRepository
import com.jetbrains.moneygenie.data.MuseumStorage
import com.jetbrains.moneygenie.screens.detail.DetailScreenModel
import com.jetbrains.moneygenie.screens.list.ListScreenModel
import com.jetbrains.moneygenie.screens.onboarding.OnBoardingScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                // TODO Fix API so it serves application/json
                json(json, contentType = ContentType.Any)
            }
        }
    }

    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }
}

val screenModelsModule = module {
    factoryOf(::ListScreenModel)
    factoryOf(::OnBoardingScreenModel)
    factoryOf(::DetailScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}