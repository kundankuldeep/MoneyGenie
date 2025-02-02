package com.jetbrains.moneygenie.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.TextWithIcon
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.Error700
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural100
import com.jetbrains.moneygenie.theme.Primary100
import com.jetbrains.moneygenie.theme.Primary300
import com.jetbrains.moneygenie.theme.Primary400
import com.jetbrains.moneygenie.theme.Primary500
import com.jetbrains.moneygenie.theme.Primary600
import com.jetbrains.moneygenie.theme.White
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.ic_cloud_download
import moneygenie.composeapp.generated.resources.ic_cloud_upload
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 15/12/24
 **/
class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProfileScreenModel = getScreenModel()
        viewModel.initViews()
        ProfileScreenComposable(viewModel)
    }

    @Composable
    fun ProfileScreenComposable(viewModel: ProfileScreenModel) {
        val navigator = LocalNavigator.currentOrThrow
        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    "My Profile",
                    showNavigationIcon = true,
                    actions = {
                        IconButton(onClick = {
                            viewModel.onEditProfileClick(navigator)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Edit Icon",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) {
            Box(Modifier.fillMaxSize().padding(it).padding(20.dp)) {
                Column {
                    // Basic info card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        val gap = 6
                        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Column {
                                Row {
                                    Text("Name: ", style = MGTypography().bodySemiBold)
                                    Text(
                                        viewModel.name.value,
                                        style = MGTypography().bodyRegular
                                    )
                                }
                                VerticalSpace(gap)
                                Row {
                                    Text("DOB: ", style = MGTypography().bodySemiBold)
                                    Text(
                                        viewModel.dob.value,
                                        style = MGTypography().bodyRegular
                                    )
                                }
                                VerticalSpace(gap)
                                Row {
                                    Text("Phone: ", style = MGTypography().bodySemiBold)
                                    Text(
                                        viewModel.phone.value,
                                        style = MGTypography().bodyRegular
                                    )
                                }
                                VerticalSpace(gap)
                                Row {
                                    Text("Email: ", style = MGTypography().bodySemiBold)
                                    Text(
                                        viewModel.email.value,
                                        style = MGTypography().bodyRegular
                                    )
                                }
                                VerticalSpace(gap)
                                Row {
                                    Text("Gender: ", style = MGTypography().bodySemiBold)
                                    Text(
                                        viewModel.gender.value,
                                        style = MGTypography().bodyRegular
                                    )
                                }
                            }
                        }
                    }

                    VerticalSpace(24)

                    // update account info
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Column {
                                ProfileScreenItem("Change Passcode", Icons.Rounded.Lock) {
                                    viewModel.onChangePasswordClick(navigator)
                                }
                                VerticalSpace(10)
                                HorizontalDivider(thickness = 1.dp, color = Natural100)
                                VerticalSpace(10)
                                ProfileScreenItem("Change Security question", Icons.Rounded.Info) {
                                    viewModel.onChangeSecurityQuestionClick(navigator)
                                }
                            }
                        }
                    }

                    VerticalSpace(24)

                    // settings
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Column {
                                ProfileScreenItem("Settings", Icons.Rounded.Settings) {
                                    viewModel.onSettingClick(navigator)
                                }
                            }
                        }
                    }

                    VerticalSpace(24)

                    // Import export
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Column {
                                ProfileScreenItem(
                                    "Import Data From File",
                                    painterResource(resource = Res.drawable.ic_cloud_download)
                                ) {
                                    viewModel.onImportClick(navigator)
                                }

                                VerticalSpace(10)
                                HorizontalDivider(thickness = 1.dp, color = Natural100)
                                VerticalSpace(10)

                                ProfileScreenItem(
                                    "Export Data To File",
                                    painterResource(resource = Res.drawable.ic_cloud_upload)
                                ) {
                                    viewModel.onExportClick(navigator)
                                }

                                VerticalSpace(10)
                                HorizontalDivider(thickness = 1.dp, color = Natural100)
                                VerticalSpace(10)

                                ProfileScreenItemWithSwitch(
                                    "Sync to Google Drive",
                                    isChecked = false,
                                    icon = painterResource(resource = Res.drawable.ic_cloud_upload)
                                ) {
                                    viewModel.onExportClick(navigator)
                                }

                                VerticalSpace(10)
                                HorizontalDivider(thickness = 1.dp, color = Natural100)
                                VerticalSpace(10)

                                ProfileScreenItem(
                                    "Sync from Google Drive",
                                    painterResource(resource = Res.drawable.ic_cloud_upload)
                                ) {
                                    viewModel.onExportClick(navigator)
                                }
                            }
                        }
                    }

                    VerticalSpace(24)

                    // Delete account
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Column {
                                ProfileScreenItem(
                                    "Clear all Data",
                                    Icons.Rounded.Clear
                                ) {
                                    viewModel.clearAllDataClick(navigator)
                                }

                                VerticalSpace(10)
                                HorizontalDivider(thickness = 1.dp, color = Natural100)
                                VerticalSpace(10)

                                ProfileScreenItem(
                                    "Delete Account",
                                    Icons.Rounded.Delete,
                                    Error700
                                ) {
                                    viewModel.deleteAccountClick(navigator)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreenItem(
    title: String,
    icon: Any,
    iconColor: Color = Primary500,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
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
            iconTint = iconColor
        )

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = "Arrow Icon",
            tint = Primary400
        )
    }
}

@Composable
fun ProfileScreenItemWithSwitch(
    title: String,
    icon: Any,
    isChecked: Boolean,
    iconColor: Color = Primary500,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithIcon(
            text = title,
            icon = icon,
            iconTint = iconColor
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