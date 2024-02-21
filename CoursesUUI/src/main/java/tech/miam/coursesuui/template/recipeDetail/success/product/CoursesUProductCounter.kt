package tech.miam.coursesuui.template.recipeDetail.success.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.sdk.components.baseComponent.counter.Counter
import com.miam.sdk.components.baseComponent.counter.CounterParameters

class CoursesUProductCounter: Counter {

    @Composable
    override fun Content(params: CounterParameters) {
        var localCount by remember(params.key ?: params.initialCount) { mutableStateOf(params.initialCount) }

        fun newValueBounded(newValue: Int): Boolean {
            return (params.minValue == null || newValue >= params.minValue!!) && (params.maxValue == null || newValue <= params.maxValue!!)
        }

        fun changedValue(localCount: Int?, delta: Int): Int? {
            // if min value is not defined 1 is surely bounded
            if (localCount == null) return params.minValue ?: 1

            if (!newValueBounded(localCount + delta)) return null

            return localCount + delta
        }

        fun increase() {
            changedValue(localCount, 1)?.let { newCount ->
                localCount = newCount
                params.onCounterChanged(newCount)
            }
        }

        fun decrease() {
            changedValue(localCount, -1)?.let { newCount ->
                localCount = newCount
                params.onCounterChanged(newCount)
            }
        }


        Row(
            modifier = Modifier.padding(
                horizontal = Dimension.sPadding,
                vertical = Dimension.sPadding
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Plus({ decrease() }, params.isDisable)
                MiddleText(localCount, params.isLoading)
                Minus({ increase() }, params.isDisable)
            }
        }
    }
}

@Composable
fun Plus(decrease: () -> Unit, isDisable: Boolean) {
    IconButton(
        onClick = { decrease() },
        enabled = !isDisable,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 70f, bottomStart = 70f))
            .background(Colors.primary)
    ) {
        Image(
            painter = painterResource(Image.less),
            contentDescription = "less icon",
            colorFilter = ColorFilter.tint(Colors.white),
            modifier = Modifier.size(Dimension.mIconHeight)
        )
    }
}

@Composable
fun MiddleText(localCount: Int?, isLoading: Boolean) {
    fun counterText(countValue: Int?): String {
        if (countValue == null) return "-"
        return localCount.toString()
    }

    Row(
        modifier = Modifier
            .background(color = Colors.primary)
            .height(48.dp)
            .width(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Colors.white, modifier = Modifier.size(16.dp))
        } else {
            Text(
                text = counterText(localCount),
                color = Colors.white,
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Minus(increase: () -> Unit, isDisable: Boolean) {
    IconButton(
        onClick = { increase() },
        enabled = !isDisable,
        modifier = Modifier
            .clip(RoundedCornerShape(topEnd = 70f, bottomEnd = 70f))
            .background(Colors.primary)
    ) {
        Image(
            painter = painterResource(Image.plus),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Colors.white),
            modifier = Modifier.size(Dimension.mIconHeight)
        )
    }
}

