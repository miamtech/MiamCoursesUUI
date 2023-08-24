package tech.miam.coursesuui.template.mealPlanner.form

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miam.kmm_miam_sdk.android.theme.Typography
import tech.miam.coursesuui.component.CounterButton
import tech.miam.coursesuui.R



data class MealCountState(var count: Int = 0)


@Composable
fun CoursesUStepper(
    defaultValue: Int = 0,
    minValue: Int = 1,
    maxValue: Int = 9,
    disableButton: Boolean = false,
    onStepperChanged: (Int) -> Unit
) {
    var value by remember { mutableStateOf(defaultValue) }

    // Calculate button colors
    val minButtonColor = if (value <= minValue || disableButton) colorResource(R.color.miam_courses_u_background_gray_light) else colorResource(R.color.miam_courses_u_primary)
    val maxButtonColor = if (value >= maxValue || disableButton) colorResource(R.color.miam_courses_u_background_gray_light) else colorResource(R.color.miam_courses_u_primary)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CounterButton(
            Icons.Default.Remove,
            value > minValue && !disableButton,
            minButtonColor
        ) {
            if (value > minValue) {
                value -= 1
                onStepperChanged(value)
            }
        }
        CounterValue(value)
        CounterButton(
            Icons.Default.Add,
            value < maxValue && !disableButton,
            maxButtonColor
        ) {
            if (value < maxValue || value < 9) {
                value += 1
                onStepperChanged(value)
            }
        }
    }
}

@Composable
fun CoursesUMealStepper(
    counterState: MealCountState,
    minValue: Int = 1,
    maxValue: Int = 9,
    disableButton: Boolean = false,
    increase: () -> Unit,
    decrease: () -> Unit,
) {

    val minButtonColor = if (counterState.count <= minValue || disableButton) colorResource(R.color.miam_courses_u_background_gray_light) else colorResource(R.color.miam_courses_u_primary)
    val maxButtonColor = if (counterState.count >= maxValue || disableButton) colorResource(R.color.miam_courses_u_background_gray_light) else colorResource(R.color.miam_courses_u_primary)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CounterButton(
            Icons.Default.Remove,
            counterState.count > minValue && !disableButton,
            minButtonColor
        ) {
            decrease()
        }
        CounterValue(counterState.count)
        CounterButton(
            Icons.Default.Add,
            counterState.count < maxValue && !disableButton,
            maxButtonColor
        ) {
            increase()
        }
    }

}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CounterValue(value: Int) {
    AnimatedContent(
        targetState = value,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            } else {
                slideInVertically { height -> -height } + fadeIn() with
                        slideOutVertically { height -> height } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) { targetCount ->
        Text(
            modifier = Modifier.width(20.dp),
            text = "${if (targetCount == 0) "0" else targetCount}",
            style = Typography.subtitleBold,
            textAlign = TextAlign.Center
        )
    }
}

