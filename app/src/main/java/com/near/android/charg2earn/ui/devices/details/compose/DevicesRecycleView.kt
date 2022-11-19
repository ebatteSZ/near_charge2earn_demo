package com.near.android.charg2earn.ui.devices.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.DialogRecyclerBackColor
import com.near.android.charg2earn.ui.devices.details.viewmodel.DevicesDetailViewModel
import com.near.android.charg2earn.ui.home.raffle.compose.BuyTicketBtn
import com.near.android.charg2earn.R


@Composable
fun DevicesRecycleView(
    viewModel: DevicesDetailViewModel = viewModel(),
    onClickCycleNow: () -> Unit,
    onClickSuccess: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BuyTicketBtn(text = R.string.device_recycle) {
            viewModel.setRecycleDialog(true)
        }
        if (viewModel.getShowDialog()) {
            DialogRecycler(viewModel) {
                onClickCycleNow()
            }
        }
        if (viewModel.isDialogSuccess()) {
            DialogRecyclerSuccess {
                onClickSuccess()

            }
        }
    }
}

@Composable
fun DialogRecycler(viewModel: DevicesDetailViewModel, onClickCycleNow: () -> Unit) {
    AlertDialog(
        modifier = Modifier
            .width(DialogWight)
            .height(DialogHeight),
        shape = MRoundedShape,
        containerColor = DialogRecyclerBackColor,
        onDismissRequest = {},
        title = {},
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_recycle_batter),
                    contentDescription = stringResource(id = R.string.device_recycle_batter)
                )
                Text(
                    text = stringResource(id = R.string.device_recycle_batter),
                    fontSize = RecycleBatterTextSize,
                    color = Color.White,
                    style = textStyleBold,
                    modifier = Modifier.padding(
                        top = RecycleBatterPaddingTop,
                        bottom = RecycleBatterPaddingBottom
                    ),
                    textAlign = TextAlign.Center
                )
            }
        },

        confirmButton = {
            DialogConfirmButton(
                onClickConfirm = {
                    onClickCycleNow()
                }, onClickCancel = {
                    viewModel.setRecycleDialog(false)
                })
        }
    )


}

private val RecycleBatterTextSize = 16.sp
private val RecycleBatterPaddingTop = 20.dp
private val RecycleBatterPaddingBottom = 10.dp
private val DialogWight = 318.dp
private val DialogHeight = 286.dp



