package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.components.mealPlanner.recipe.placeholder.MealPlannerRecipePlaceholder
import com.miam.sdk.components.mealPlanner.recipe.placeholder.MealPlannerRecipePlaceholderParameters

class MealPlannerRecipePlaceholderU: MealPlannerRecipePlaceholder {
    @Composable
    override fun Content(params: MealPlannerRecipePlaceholderParameters) {
        Card(
            modifier = Modifier
                .height(Dimension.mealPlannerCardHeight)
                .clickable {
                    params.action()
                }
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Colors.primary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        ,
                    contentAlignment = Alignment.Center,
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawCircle(
                            color = Colors.white
                        )
                        drawCircle(
                            color = Colors.primary,
                            radius = 12.dp.toPx()
                        )
                    }
                    Image(
                        painter = painterResource(id = Image.plus),
                        colorFilter = ColorFilter.tint(Colors.white),
                        contentDescription = "Plus",
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    Localisation.Budget.addMealIdea.localised,
                    style = Typography.bodyBold,
                    color = Colors.white,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MealPlannerRecipePlaceholderImpPreview() {
    MealPlannerRecipePlaceholderU().Content(params = MealPlannerRecipePlaceholderParameters { print("hello") })
}