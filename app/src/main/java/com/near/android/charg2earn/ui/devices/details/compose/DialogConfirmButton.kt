package com.near.android.charg2earn.ui.devices.details.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.Black2
import com.near.android.charg2earn.ui.compose.theme.DialogRecyclerCancel
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.R


@Composable
fun DialogConfirmButton(
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier
                .weight(1f)
                .width(RecyclerWidth)
                .height(CancelHeight),
            onClick = { onClickConfirm() },
            colors = ButtonDefaults.buttonColors(GreenColor),
            shape = MRoundedShape,
            contentPadding = PaddingValues(0.dp),

            ) {
            Text(
                stringResource(id = R.string.device_recycle_now),
                color = Black2,
                style = textStyleBold,
                fontSize = RecyclerNowSize
            )
        }
        Spacer(modifier = Modifier.width(SpacerWidth))
        Button(
            modifier = Modifier
                .weight(1f)
                .width(CancelWidth)
                .height(CancelHeight),
            onClick = {
                onClickCancel()
            },
            colors = ButtonDefaults.buttonColors(DialogRecyclerCancel),
            shape = MRoundedShape,
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                stringResource(id = R.string.device_recycle_cancel),
                color = Black2,
                style = textStyleBold,
                fontSize = CancelSize
            )
        }
    }
}

private val CancelSize = 14.sp
private val CancelWidth = 126.dp
private val CancelHeight = 46.dp
private val SpacerWidth = 25.dp
private val RecyclerWidth = 130.dp
private val RecyclerNowSize = 14.sp