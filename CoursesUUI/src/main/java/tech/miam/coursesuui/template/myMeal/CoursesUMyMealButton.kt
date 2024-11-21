package tech.miam.coursesuui.template.myMeal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.components.baseComponent.myMealButton.success.MyMealButtonSuccess
import ai.mealz.sdk.components.baseComponent.myMealButton.success.MyMealButtonSuccessParameters
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.theme.Dimension
import ai.mealz.sdk.theme.Typography
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource

class CoursesUMyMealButton: MyMealButtonSuccess {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content(params: MyMealButtonSuccessParameters) {
        Box(
            modifier = Modifier.fillMaxWidth(), // Makes the Box take full width, allowing centering within it
            contentAlignment = Alignment.Center // Centers its content horizontally
        ) {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically { height -> height },
                exit = slideOutVertically { height -> height }
            ) {
                Surface(
                    modifier = Modifier.padding(Dimension.mPadding),
                    color = Colors.primary,
                    shape = RoundedCornerShape(100.dp),
                    onClick = params.onClick
                ) {
                    Row(
                        modifier = Modifier.padding(Dimension.lPadding),
                        horizontalArrangement = Arrangement.spacedBy(Dimension.lPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = Image.meals),
                            contentDescription = "icon categories page floating",
                            modifier = Modifier
                                .size(16.dp),
                            tint = Colors.white
                        )
                        Text(
                            text = Localisation.myMeals.mealsAdded(params.recipeCount).localised,
                            style = Typography.bodyBold,
                            color = Colors.white
                        )
                        Icon(
                            painterResource(id = Image.previous),
                            contentDescription = "icon button categories page floating",
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer(rotationZ = 180f),
                            tint = Colors.white
                        )
                    }
                }
            }
        }
    }
}
