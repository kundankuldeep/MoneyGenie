package com.jetbrains.moneygenie.screens.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.add_recipients
import moneygenie.composeapp.generated.resources.crawl_1_sub_title
import moneygenie.composeapp.generated.resources.crawl_1_title
import moneygenie.composeapp.generated.resources.crawl_2_sub_title
import moneygenie.composeapp.generated.resources.crawl_2_title
import moneygenie.composeapp.generated.resources.crawl_3_sub_title
import moneygenie.composeapp.generated.resources.crawl_3_title
import moneygenie.composeapp.generated.resources.crawl_4_sub_title
import moneygenie.composeapp.generated.resources.crawl_4_title
import moneygenie.composeapp.generated.resources.get_insights
import moneygenie.composeapp.generated.resources.manage_transactions
import moneygenie.composeapp.generated.resources.send_reminders
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/**
 * Created by Kundan on 18/08/24
 **/
class OnBoardingScreenModel : ScreenModel {
    fun isLoggedIn(): Boolean {
        return false
    }

    val crawlers = listOf(
        CrawlerModel(
            Res.drawable.manage_transactions,
            Res.string.crawl_1_title,
            Res.string.crawl_1_sub_title
        ), CrawlerModel(
            Res.drawable.add_recipients,
            Res.string.crawl_2_title,
            Res.string.crawl_2_sub_title
        ), CrawlerModel(
            Res.drawable.get_insights,
            Res.string.crawl_3_title,
            Res.string.crawl_3_sub_title
        ), CrawlerModel(
            Res.drawable.send_reminders,
            Res.string.crawl_4_title,
            Res.string.crawl_4_sub_title
        )
    )
}

data class CrawlerModel(
    val icon: DrawableResource,
    val title: StringResource,
    val subTitle: StringResource
)