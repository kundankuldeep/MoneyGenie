package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.Error700
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural300
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary700
import kotlin.math.abs

/**
 * Created by Kundan on 03/01/25
 **/
@Composable
fun AccountStatusCard(amountLent: Double, amountBorrowed: Double) {
    val diffAmount = amountLent - amountBorrowed
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Amount Lent",
                        style = MGTypography().bodyRegularXS,
                        color = Natural300
                    )
                    VerticalSpace(10)
                    Text(
                        amountLent.toString(),
                        style = MGTypography().titleSemiBoldL,
                        color = Primary700
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Amount Borrowed",
                        style = MGTypography().bodyRegularXS,
                        color = Natural300
                    )
                    VerticalSpace(10)
                    Text(
                        amountBorrowed.toString(),
                        style = MGTypography().titleSemiBoldL,
                        color = Error700
                    )
                }
            }
            VerticalSpace(10)
            HorizontalDivider(thickness = 1.dp)
            VerticalSpace(10)
            Row {
                Text(
                    "Overall Status: ",
                    style = MGTypography().bodyRegular,
                    color = Natural500
                )
                Text(
                    text = if (diffAmount == 0.0) "Account is settled" else
                        "${if (diffAmount > 0) "You Owe " else "They Borrowed "}₹${abs(diffAmount)}",
                    style = MGTypography().bodySemiBold,
                    color = if (diffAmount >= 0) Primary700 else Error700
                )
            }
        }
    }
}