package com.near.android.charg2earn.ui.devices.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.DialogRecyclerBackColor
import com.near.android.charg2earn.R


@Composable
fun DialogRecyclerSuccess(onDismissRequest: () -> Unit) {
    AlertDialog(
        shape = MRoundedShape,
        containerColor = DialogRecyclerBackColor,
        onDismissRequest = { onDismissRequest() },
        title = {},
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(DialogWight)
                    .height(RecyclingSuccessfulHeight)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_recycling_successfu),
                    contentDescription = stringResource(id = R.string.device_recycle_batter),
                    modifier = Modifier.padding(top = RecyclingSuccessfulPaddingTop)
                )
                Text(
                    text = stringResource(id = R.string.device_recycle_successful),
                    fontSize = RecyclingSuccessfulDescSize,
                    color = Color.White,
                    style = textStyleBold,
                    modifier = Modifier.padding(top = RecyclingSuccessfulPaddingTop),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {}
    )
}

private val DialogWight = 318.dp
private val RecyclingSuccessfulHeight = 200.dp
private val RecyclingSuccessfulPaddingTop = 20.dp
private val RecyclingSuccessfulDescSize = 16.sp


@Preview
@Composable
fun ShowDialog() {
    DialogRecyclerSuccess {}
}