package com.jetbrains.moneygenie.screens.home

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.moneygenie.data.models.Recipient

/**
 * Created by Kundan on 26/09/24
 **/
class HomeScreenModel : ScreenModel {
    val dataList = mutableStateOf<ArrayList<Recipient>>(arrayListOf())
}

