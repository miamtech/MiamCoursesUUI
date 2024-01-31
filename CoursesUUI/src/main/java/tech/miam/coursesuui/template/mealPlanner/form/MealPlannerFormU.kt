package tech.miam.coursesuui.template.mealPlanner.form

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.base.state.ComponentUiState
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.components.mealPlanner.form.success.MealPlannerFormSuccess
import com.miam.sdk.components.mealPlanner.form.success.MealPlannerFormSuccessParameters

import tech.miam.coursesuui.component.CoursesUButton
import tech.miam.coursesuui.R


class CoursesUBudgetForm : MealPlannerFormSuccess {
    @Composable
    override fun Content(params: MealPlannerFormSuccessParameters) {
        val focusManager = LocalFocusManager.current
        Box(
            modifier = Modifier
                .background(colorResource(R.color.miam_courses_u_background_blue))
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                },
            contentAlignment = Alignment.TopCenter,
        ) {
            PageBackground()
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimension.lPadding),
                modifier = Modifier
                    .padding(Dimension.lPadding)
            ) {
                Image(
                    painter = painterResource(R.drawable.budget_repas_logo),
                    contentDescription = "Background",
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()
                )
                FormCard(params)
            }
        }
    }
}


@Composable
fun PageBackground() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.white_wave),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.FillBounds
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.budget_left_side_bg),
                contentDescription = "Background",
                modifier = Modifier
                    .height(250.dp),
                contentScale = ContentScale.FillHeight
            )

            Image(
                painter = painterResource(R.drawable.budget_right_side_bg),
                contentDescription = "Background",
                modifier = Modifier
                    .height(180.dp),
                contentScale = ContentScale.FillHeight
            )
        }
    }
}


@Composable
fun FormCard(params: MealPlannerFormSuccessParameters, onSubmit: () -> Unit = {}) {

    val focusManager = LocalFocusManager.current
    val isKeyBoardOpen by keyboardAsState()

    fun calculateMealInitialValue(): Int {
        if (params.numberOfMeals > params.maxMealCount) {
            return params.maxMealCount
        }
        return params.numberOfMeals
    }

    val mealCounterState = MealCountState(
        calculateMealInitialValue()
    )

    fun budgetAndPeopleValid(): Boolean {
        return params.budget > 0
    }

    @Composable
    fun submitBackgroundColor(): Color {
        return if (budgetAndPeopleValid()) (colorResource(R.color.miam_courses_u_primary))
        else colorResource(R.color.miam_courses_u_background_gray)
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(size = Dimension.mRoundedCorner))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = colorResource(R.color.miam_courses_u_background_gray),
                shape = RoundedCornerShape(size = Dimension.mRoundedCorner)
            )
            .padding(Dimension.lPadding)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            },
        verticalArrangement = Arrangement.spacedBy(Dimension.lPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choisissez vos repas de la semaine ou du mois selon votre budget :",
            color = Colors.black,
            style = Typography.bodyBold,
            textAlign = TextAlign.Center
        )
        Divider(color = colorResource(R.color.miam_courses_u_background_gray))
        CoursesUFormRow(
            caption = "Mon budget max",
            icon = painterResource(id = R.drawable.budget_icon)
        ) {
            CoursesUBudgetInt(
                budgetAmount = params.budget,
                onBudgetChanged = { amount ->
                    params.setBudget(amount)
                    params.refreshMaxMealCount(
                        amount,
                        params.numberOfGuests
                    )
                })
            CoursesUCurrency(text = "€")
        }
        Divider(color = colorResource(R.color.miam_courses_u_background_gray))
        CoursesUFormRow(
            caption = "Nombre de personnes",
            icon = painterResource(id = R.drawable.number_of_people_icon)
        ) {
            CoursesUStepper(
                defaultValue = params.numberOfGuests,
                onStepperChanged = {
                    params.setNumberOfGuests(it)
                    params.refreshMaxMealCount(
                        params.budget,
                        it
                    )
                })
        }
        Divider(color = colorResource(R.color.miam_courses_u_background_gray))
        CoursesUFormRow(
            caption = "Nombre de repas",
            icon = painterResource(id = R.drawable.number_of_meals_icon)
        ) {
            CoursesUMealStepper(
                mealCounterState,
                disableButton = false,
                maxValue = if (params.maxMealCount > 9) 9 else params.maxMealCount,
                increase = {
                    if (mealCounterState.count < params.maxMealCount || mealCounterState.count < 9) {
                        params.setNumberOfMeals(mealCounterState.count + 1)
                    }
                })
            {
                if (mealCounterState.count > 0) {
                    params.setNumberOfMeals(mealCounterState.count - 1)
                }
            }
        }
        Divider(color = colorResource(R.color.miam_courses_u_background_gray))
        CoursesUButton(
            mealCounterState,
            backgroundColor = submitBackgroundColor(),
            cornerRadius = 50.dp,
            enabled = (params.uiState != ComponentUiState.EMPTY) && (isKeyBoardOpen == Keyboard.Closed),
            paddingValues = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
            content = {
                Row(
                    Modifier
                        .height(30.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (params.uiState == ComponentUiState.LOADING) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(Dimension.lIconHeight)
                                .padding(top = 4.dp, end = 4.dp),
                            strokeWidth = 2.dp,
                            color = Colors.white
                        )
                    } else {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Colors.white
                        )
                        Text(
                            text = "C'est parti !",
                            color = Colors.white,
                            style = Typography.subtitle.copy(fontSize = 16.sp)
                        )
                    }
                }

            }
        ) {
            params.submit(
                params.budget,
                params.numberOfMeals,
                params.numberOfGuests
            )
            onSubmit()
        }
    }
}


@Composable
fun CoursesUFormRow(
    caption: String? = null,
    icon: Painter,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = Dimension.sPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = icon,
            contentDescription = "Icon",
            modifier = Modifier
                .size(Dimension.xlButtonHeight) // Modify according to your dimensions
                .padding(Dimension.sPadding)
        )

        if (!caption.isNullOrEmpty()) {
            Text(
                text = caption,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Dimension.sPadding),
                style = Typography.body.copy(fontSize = 14.sp)
            )
            Box(modifier = Modifier.weight(0.5f))
        }

        Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.CenterEnd) {
            content()
        }
    }
}

enum class Keyboard {
    Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }
    return keyboardState
}

@Preview
@Composable
fun CoursesUBudgetFormPreview() {

}
