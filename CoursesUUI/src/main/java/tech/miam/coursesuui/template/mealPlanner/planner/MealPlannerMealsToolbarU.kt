package tech.miam.coursesuui.template.mealPlanner.planner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ai.mealz.core.base.state.ComponentUiState
import tech.miam.coursesuui.R
import tech.miam.coursesuui.template.mealPlanner.form.FormCard
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Dimension
import ai.mealz.sdk.theme.Typography
import ai.mealz.sdk.components.mealPlanner.form.success.MealPlannerFormSuccessParameters
import ai.mealz.sdk.components.mealPlanner.meals.toolbar.MealPlannerToolbar
import kotlinx.coroutines.flow.MutableStateFlow

class CoursesUBudgetPlannerToolbar: MealPlannerToolbar {
    @Composable
    override fun Content(params: MealPlannerFormSuccessParameters) {
        val showFullForm = remember { mutableStateOf(false) }
        val currentRemainingRecipeCountState =  params.currentRemainingRecipeCountFlow.collectAsState(
            initial = 0
        )

        Box() {
            Image(
                painter = painterResource(R.drawable.white_wave),
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(Modifier.padding(horizontal = 16.dp)) {
                Image(
                    painter = painterResource(R.drawable.budget_repas_logo),
                    contentDescription = "Background",
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .clickable {
                            showFullForm.value = false
                        }
                        .fillMaxWidth()
                )
                if (!showFullForm.value) {
                    ClickableToolbar(params) {
                        showFullForm.value = true
                    }
                }
                AnimatedVisibility(visible = showFullForm.value) {
                    FormCard(params) { showFullForm.value = false }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.miam_courses_u_background_blue))
                        .padding(top = 15.dp),
                    text = "${currentRemainingRecipeCountState.value} idée${if (currentRemainingRecipeCountState.value > 1) "s" else ""} repas pour votre budget :",
                    color = Colors.black,
                    style = Typography.bodyBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun ClickableToolbar(mealPlannerFormParameters: MealPlannerFormSuccessParameters, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(size = Dimension.mRoundedCorner))
            .border(
                width = 1.dp,
                color = colorResource(R.color.miam_courses_u_background_gray_light),
                shape = RoundedCornerShape(size = Dimension.mRoundedCorner)
            )

            .background(Colors.white)
            .height(60.dp)
            .fillMaxWidth(),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CoursesUTemplateRow(
                caption = (mealPlannerFormParameters.budget.toString() + "€"),
                icon = painterResource(id = R.drawable.budget_icon)
            )

            VerticalDivider()
            CoursesUTemplateRow(
                caption = mealPlannerFormParameters.numberOfGuests.toString(),
                icon = painterResource(id = R.drawable.number_of_people_icon)
            )
            VerticalDivider()
            CoursesUTemplateRow(
                caption = mealPlannerFormParameters.numberOfMeals.toString(),
                icon = painterResource(id = R.drawable.number_of_meals_icon)
            )
        }
    }

}

@Composable
fun CoursesUTemplateRow(
    caption: String,
    icon: Painter
) {

    Row(
        modifier = Modifier
            .height(70.dp) // The hardcoded height, adjust according to your preference.
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
        Text(
            text = caption,
            modifier = Modifier
                .padding(horizontal = Dimension.sPadding), // Modify according to your dimensions
            style = Typography.body // Modify according to your style
        )
    }
}

@Composable
fun VerticalDivider(
    color: Color = colorResource(R.color.miam_courses_u_background_gray_light),
    thickness: Dp = Dp(1f)
) {
    Box(
        modifier = Modifier
            .width(thickness) // thickness of your divider
            .fillMaxHeight() // it should fill the entire height
            .background(color) // color of the divider
    )
}

@Preview
@Composable
fun CoursesUBudgetPlannerToolbarPreview() {
    Box(
        modifier = Modifier
            .background(colorResource(R.color.miam_courses_u_background_blue))
            .padding(12.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CoursesUBudgetPlannerToolbar().Content(params = MealPlannerFormSuccessParameters(
            budget = 40,
            numberOfGuests = 4,
            numberOfMeals = 4,
            uiState = ComponentUiState.SUCCESS,
            setBudget = {},
            setNumberOfGuests = { },
            setNumberOfMeals = {},
            12,
            MutableStateFlow(12),
            { _, _ -> },
            submit = { budget, numberOfGuests, numberOfMeal -> print("hello") }
        ))
    }
}
