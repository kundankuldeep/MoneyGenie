package com.jetbrains.moneygenie.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.moneygenie.data.MuseumObject
import com.jetbrains.moneygenie.data.MuseumRepository
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val museumRepository: MuseumRepository) : ScreenModel {
    fun getObject(objectId: Int): Flow<MuseumObject?> =
        museumRepository.getObjectById(objectId)
}
