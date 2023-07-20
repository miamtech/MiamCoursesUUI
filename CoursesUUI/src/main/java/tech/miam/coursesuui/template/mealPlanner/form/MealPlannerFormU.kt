package tech.miam.coursesuui.template.mealPlanner.form

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miam.core.sdk.base.state.ComponentUiState
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.templateInterfaces.mealPlanner.form.MealPlannerForm
import com.miam.sdk.templateParameters.mealPlanner.form.MealPlannerFormParameters
import tech.miam.coursesuui.component.CoursesUButton
import tech.miam.coursesuui.R


class CoursesUBudgetForm: MealPlannerForm {

    @Composable
    override fun Content(mealPlannerFormParameters: MealPlannerFormParameters) {
        Box(
            modifier = Modifier
                .background(colorResource(R.color.miam_courses_u_background_blue))
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            PageBackground()
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimension.lPadding),
                modifier = Modifier.padding(Dimension.lPadding)
            ) {
                Image(
                    painter = painterResource(R.drawable.budget_repas_logo),
                    contentDescription = "Background",
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()
                )
                FormCard(mealPlannerFormParameters)
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
fun FormCard(mealPlannerFormParameters: MealPlannerFormParameters, onSubmit: () -> Unit = {}) {

    fun calculateMealInitialValue(): Int {
        if (mealPlannerFormParameters.numberOfMeals > mealPlannerFormParameters.maxMealCount) {
            return mealPlannerFormParameters.maxMealCount
        }
        return mealPlannerFormParameters.numberOfMeals
    }

    val mealCounterState = MealCountState(
        calculateMealInitialValue()
    )

    fun budgetAndPeopleValid(): Boolean {
        return mealPlannerFormParameters.budget > 0
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
            .padding(Dimension.lPadding),
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
                budgetAmount = mealPlannerFormParameters.budget,
                onBudgetChanged = { amount ->
                    mealPlannerFormParameters.setBudget(amount)
                    mealPlannerFormParameters.refreshMaxMealCount(amount, mealPlannerFormParameters.numberOfGuests)
                })
            CoursesUCurrency(text = "€")
        }
        Divider(color = colorResource(R.color.miam_courses_u_background_gray))
        CoursesUFormRow(
            caption = "Nombre de personnes",
            icon = painterResource(id = R.drawable.number_of_people_icon)
        ) {
            CoursesUStepper(
                defaultValue = mealPlannerFormParameters.numberOfGuests,
                onStepperChanged = {
                    mealPlannerFormParameters.setNumberOfGuests(it)
                    mealPlannerFormParameters.refreshMaxMealCount(mealPlannerFormParameters.budget, it)
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
                maxValue = if (mealPlannerFormParameters.maxMealCount > 9) 9 else mealPlannerFormParameters.maxMealCount,
                increase = {
                    if (mealCounterState.count < mealPlannerFormParameters.maxMealCount || mealCounterState.count < 9) {
                        mealPlannerFormParameters.setNumberOfMeals(mealCounterState.count + 1)
                    }
                })
            {
                if (mealCounterState.count > 0) {
                    mealPlannerFormParameters.setNumberOfMeals(mealCounterState.count - 1)
                }
            }
        }
        Divider(color = colorResource(R.color.miam_courses_u_background_gray))
        CoursesUButton(
            backgroundColor = submitBackgroundColor(),
            cornerRadius = 50.dp,
            enabled = mealPlannerFormParameters.uiState != ComponentUiState.EMPTY,
            paddingValues = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
            content = {
                Row(
                    Modifier.height(30.dp)
                        .width(200.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (mealPlannerFormParameters.uiState == ComponentUiState.LOADING) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(Dimension.lIconHeight)
                                .padding(top = 4.dp),
                            strokeWidth = 2.dp,
                            color = Colors.white
                        )
                    } else {
                        Icon(
                            Icons.Default.Search,
                            modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                            contentDescription = "Search",
                            tint = Colors.white
                        )
                        Text(text = "C'est parti !", color = Colors.white, style = Typography.subtitle)
                    }
                }

            }
        ) {
            mealPlannerFormParameters.submit(
                mealPlannerFormParameters.budget,
                mealPlannerFormParameters.numberOfMeals,
                mealPlannerFormParameters.numberOfGuests
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
                style = Typography.body
            )
            Box(modifier = Modifier.weight(0.5f))
        }

        Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.CenterEnd) {
            content()
        }
    }
}


@Preview
@Composable
fun CoursesUBudgetFormPreview() {
    Box(modifier = Modifier.background(Colors.white)) {
        FormCard(mealPlannerFormParameters = MealPlannerFormParameters(
            budget = 40,
            numberOfGuests = 4,
            numberOfMeals = 4,
            uiState = ComponentUiState.SUCCESS,
            setBudget = {},
            setNumberOfGuests = { },
            setNumberOfMeals = {},
            12,
            12,
            { _, _ -> },
            submit = { budget, numberOfGuests, numberOfMeal -> print("hello") }
        ))
    }
}
