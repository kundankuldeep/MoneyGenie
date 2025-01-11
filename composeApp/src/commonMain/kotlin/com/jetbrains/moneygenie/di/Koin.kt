package com.jetbrains.moneygenie.di

import com.jetbrains.moneygenie.data.local.RealmManager
import com.jetbrains.moneygenie.data.local.RealmManagerImpl
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepositoryImpl
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepositoryImpl
import com.jetbrains.moneygenie.screens.authentication.LoginScreenModel
import com.jetbrains.moneygenie.screens.authentication.SignUpScreenModel
import com.jetbrains.moneygenie.screens.home.HomeScreenModel
import com.jetbrains.moneygenie.screens.onboarding.OnBoardingScreenModel
import com.jetbrains.moneygenie.screens.profile.ProfileScreenModel
import com.jetbrains.moneygenie.screens.profile.editProfile.EditProfileScreenModel
import com.jetbrains.moneygenie.screens.recipient.RecipientScreenModel
import com.jetbrains.moneygenie.screens.recipient.addRecipients.AddRecipientScreenModel
import com.jetbrains.moneygenie.screens.recipient.editRecipients.EditRecipientsScreenModel
import com.jetbrains.moneygenie.screens.settings.SettingsScreenModel
import com.jetbrains.moneygenie.screens.transactions.AddTransactionScreenModel
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

    // Provide RealmManager
    single<RealmManager> {
        RealmManagerImpl().apply {
            init() // Initialize RealmManager
        }
    }

    // Provide RecipientRepository
    single<RecipientRepository> {
        RecipientRepositoryImpl(get()) // Inject RealmManager
    }

    // Provide TransactionRepository
    single<TransactionRepository> {
        TransactionRepositoryImpl(get(), get()) // Inject RealmManager
    }
}

val screenModelsModule = module {
    factoryOf(::OnBoardingScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::AddRecipientScreenModel)
    factoryOf(::AddTransactionScreenModel)
    factoryOf(::SignUpScreenModel)
    factoryOf(::LoginScreenModel)
    factoryOf(::RecipientScreenModel)
    factoryOf(::SettingsScreenModel)
    factoryOf(::ProfileScreenModel)
    factoryOf(::EditProfileScreenModel)
    factoryOf(::EditRecipientsScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}
