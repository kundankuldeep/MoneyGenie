package com.jetbrains.moneygenie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetbrains.moneygenie.theme.Error700
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural300
import com.jetbrains.moneygenie.theme.Primary700
import kotlin.math.abs

/**
 * Created by Kundan on 03/01/25
 **/

@Composable
fun AccountStatusCard(amountLent: Double, amountBorrowed: Double) {
    val diffAmount = amountLent - amountBorrowed
    val statusColor = when {
        diffAmount > 0 -> Primary700
        diffAmount < 0 -> Error700
        else -> Color.Gray
    }

    val statusText = when {
        diffAmount > 0 -> "Receivable Amount"
        diffAmount < 0 -> "Balance Payable"
        else -> "Account is Settled"
    }

    val statusIcon = when {
        diffAmount > 0 -> "⬆️"  // Up arrow for lending more
        diffAmount < 0 -> "⬇️"  // Down arrow for borrowing more
        else -> "✅"  // Checkmark for settled account
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {

            // 🔹 Status Row (Compact & More Readable)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(
                        statusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(10.dp)
                    ).padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = statusText, // Smaller text
                    style = MGTypography().bodyRegularXS,
                    color = statusColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = statusIcon,  // Icon
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 6.dp) // Add some spacing
                    )
                    Text(
                        text = "₹${abs(diffAmount)}",
                        style = MGTypography().titleSemiBoldL, // Large amount
                        color = statusColor
                    )
                }
            }

            VerticalSpace(10)

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Amount Lent",
                        style = MGTypography().bodyRegularXS,
                        color = Natural300
                    )
                    VerticalSpace(6)
                    Text(
                        "₹${abs(amountLent)}",
                        style = MGTypography().titleSemiBold,
                        color = Primary700
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Amount Borrowed",
                        style = MGTypography().bodyRegularXS,
                        color = Natural300
                    )
                    VerticalSpace(6)
                    Text(
                        "₹${abs(amountBorrowed)}",
                        style = MGTypography().titleSemiBold,
                        color = Error700
                    )
                }
            }
        }
    }
}