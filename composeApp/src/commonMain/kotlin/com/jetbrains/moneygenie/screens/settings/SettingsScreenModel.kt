package com.jetbrains.moneygenie.screens.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.screens.settings.webPage.WebViewScreen

/**
 * Created by Kundan on 15/12/24
 **/
class SettingsScreenModel : ScreenModel {
    var navigator: Navigator? = null

    private val URL_ABOUT = "https://kundankuldeep.github.io/MoneyGenieWeb/About/about.html"
    private val URL_T_C =
        "https://kundankuldeep.github.io/MoneyGenieWeb/TermAndCondition/termandcondition.html"
    private val URL_PP =
        "https://kundankuldeep.github.io/MoneyGenieWeb/PrivacyPolicy/privacypolicy.html"
    private val URL_WRITE_TO_US =
        "https://kundankuldeep.github.io/MoneyGenieWeb/contact/contact.html"
    private val URL_HELP_SUPPORT =
        "https://kundankuldeep.github.io/MoneyGenieWeb/helpAndSupport/help.html"

    fun onAboutClick() {
        navigator?.push(WebViewScreen(URL_ABOUT, "About"))
    }

    fun onPrivacyPolicyClick() {
        navigator?.push(WebViewScreen(URL_PP, "Privacy Policy"))
    }

    fun onTermAndConditionClick() {
        navigator?.push(WebViewScreen(URL_T_C, "Term And Condition"))
    }

    fun onWriteToUsClick() {
        navigator?.push(WebViewScreen(URL_WRITE_TO_US, "Write To Us"))
    }

    fun onHelpAndSupportClick() {
        navigator?.push(WebViewScreen(URL_HELP_SUPPORT, "Help And Support"))
    }
}