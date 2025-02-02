package com.jetbrains.moneygenie.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.TextWithIcon
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural100
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary100
import com.jetbrains.moneygenie.theme.Primary300
import com.jetbrains.moneygenie.theme.Primary400
import com.jetbrains.moneygenie.theme.Primary500
import com.jetbrains.moneygenie.theme.Primary600
import com.jetbrains.moneygenie.theme.White
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.ic_bell
import moneygenie.composeapp.generated.resources.ic_document
import moneygenie.composeapp.generated.resources.ic_feedback
import moneygenie.composeapp.generated.resources.ic_help_support
import moneygenie.composeapp.generated.resources.ic_info_icon
import moneygenie.composeapp.generated.resources.ic_languages
import moneygenie.composeapp.generated.resources.ic_privecy_policy
import moneygenie.composeapp.generated.resources.ic_rate_us
import moneygenie.composeapp.generated.resources.ic_share_icon
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 15/12/24
 **/
class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: SettingsScreenModel = getScreenModel()
        screenModel.navigator = navigator
        SettingsScreenComposable(screenModel, navigator)
    }
}

@Composable
fun SettingsScreenComposable(viewModel: SettingsScreenModel, navigator: Navigator) {
    MGScaffold(
        topBar = {
            MainAppBar(
                navigator,
                "Settings",
                showNavigationIcon = true
            )
        }
    ) {
        Box(Modifier.fillMaxSize().padding(it)) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                VerticalSpace(24)
                // App Preferences
                SettingSectionTitle("App Preferences")

                var notificationsEnabled by remember { mutableStateOf(true) }
                SettingScreenItemWithSwitch(
                    title = "Notifications",
                    icon = painterResource(resource = Res.drawable.ic_bell),
                    isChecked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
//                SettingScreenItem("Theme", painterResource(resource = Res.drawable.ic_theme)) {}
                SettingScreenItem(
                    "Language",
                    painterResource(resource = Res.drawable.ic_languages)
                ) {}

                // Support And Info
                VerticalSpace(30)
                SettingSectionTitle("Support And Info")

                SettingScreenItem(
                    "Help & Support",
                    painterResource(resource = Res.drawable.ic_help_support)
                ) { viewModel.onHelpAndSupportClick() }
                SettingScreenItem(
                    "Write To Us",
                    painterResource(resource = Res.drawable.ic_feedback)
                ) { viewModel.onWriteToUsClick() }
                SettingScreenItem(
                    "Share App",
                    painterResource(resource = Res.drawable.ic_share_icon)
                ) {}
                SettingScreenItem("Rate Us", painterResource(resource = Res.drawable.ic_rate_us)) {}
                SettingScreenItem("About", painterResource(resource = Res.drawable.ic_info_icon)) {
                    viewModel.onAboutClick()
                }
                SettingScreenItem(
                    "Privacy Policy",
                    painterResource(resource = Res.drawable.ic_privecy_policy)
                ) { viewModel.onPrivacyPolicyClick() }
                SettingScreenItem(
                    "Term and Condition",
                    painterResource(resource = Res.drawable.ic_document)
                ) { viewModel.onTermAndConditionClick() }
            }
        }
    }
}

@Composable
fun SettingSectionTitle(title: String) {
    Text(title, style = MGTypography().bodyRegular, color = Natural500)
    VerticalSpace(16)
}

@Composable
fun SettingScreenItem(title: String, icon: Any, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Natural100)
    ) {
        Box(modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onClick.invoke() },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextWithIcon(
                    text = title,
                    icon = icon,
                    iconTint = Primary500
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "Arrow Icon",
                    tint = Primary400
                )
            }
        }
    }
    VerticalSpace(10)
}

@Composable
private fun SettingScreenItemWithSwitch(
    title: String,
    icon: Any,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Natural100)
    ) {
        Box(modifier = Modifier.padding(vertical = 6.dp, horizontal = 6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithIcon(
                    text = title,
                    icon = icon,
                    iconTint = Primary500
                )

                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Primary600,
                        checkedTrackColor = Primary100,
                        checkedBorderColor = Primary100,
                        uncheckedBorderColor = Primary300,
                        uncheckedTrackColor = White,
                        uncheckedThumbColor = Primary400
                    )

                )
            }
        }
    }
    VerticalSpace(10)
}
