package com.near.android.charg2earn.base.composeui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.R


private const val CELL_COUNT = 2
private const val CELL_COUNT2 = 1

@ExperimentalFoundationApi
val Span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(CELL_COUNT) }

@ExperimentalFoundationApi
val SpanItems: (LazyGridItemSpanScope.(index: Int) -> GridItemSpan) =
    { GridItemSpan(CELL_COUNT2) }

val textStyleBold
    get() = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily(Font(R.font.montserrat_semibold))
    )

val textStyleMedium
    get() = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.montserrat_medium))
    )

val MRoundedShape = RoundedCornerShape(12.dp)


@Composable
fun CustomMenuBar(
    modifier: Modifier = Modifier,
    tintColor: Color = Color.Black,
    size: Int = 24,
    @DrawableRes imageMenu: Int,
    onClickMenu: () -> Unit
) {
    Icon(
        painter = painterResource(imageMenu),
        contentDescription = "Localized description",
        modifier = modifier
            .singleClick { onClickMenu() }
            .padding(horizontal = MenuPaddingHorizontal)
            .size(size.dp),
        tint = tintColor
    )
}

private val MenuPaddingHorizontal = 15.dp

const val VIEW_CLICK_INTERVAL_TIME = 1000

@Composable
inline fun Modifier.singleClick(
    time: Int = VIEW_CLICK_INTERVAL_TIME,
    crossinline onClick: () -> Unit
): Modifier {
    var lastClickTime = remember { 0L }
    return myClickable {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - time >= lastClickTime) {
            onClick()
            lastClickTime = currentTimeMillis
        }
    }
}


fun Modifier.myClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = onClick,
        role = role,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

@Composable
fun DialogBoxLoading(
    cornerRadius: Dp = 16.dp,
    paddingStart: Dp = 50.dp,
    paddingEnd: Dp = 50.dp,
    paddingTop: Dp = 22.dp,
    paddingBottom: Dp = 22.dp,
    progressIndicatorColor: Color = GreenColor,
    progressIndicatorSize: Dp = 60.dp
) {

    Dialog(
        onDismissRequest = {
        }
    ) {
        Surface(
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd, top = paddingTop),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProgressIndicatorLoading(
                    progressIndicatorSize = progressIndicatorSize,
                    progressIndicatorColor = progressIndicatorColor
                )

                // Gap between progress indicator and text
                Spacer(modifier = Modifier.height(30.dp))

                // Please wait text
                Text(
                    modifier = Modifier
                        .padding(bottom = paddingBottom),
                    text = "Please wait...",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(
                            Font(R.font.montserrat_medium, FontWeight.Medium)
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                4.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}
