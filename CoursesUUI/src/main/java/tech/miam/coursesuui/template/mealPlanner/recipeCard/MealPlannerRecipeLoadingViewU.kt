package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.sdk.components.mealPlanner.recipe.loading.Component
import com.miam.sdk.components.mealPlanner.recipe.loading.MealPlannerRecipeLoading
import com.miam.sdk.components.mealPlanner.recipe.loading.MealPlannerRecipeLoadingParameters


class RecipeLoadingViewU: MealPlannerRecipeLoading {
    @Composable
    override fun Content(mealPlannerRecipeLoadingParameters: MealPlannerRecipeLoadingParameters) {
        when (mealPlannerRecipeLoadingParameters.displayComponent) {
            Component.PLANNER -> ShimmerMealPlannerRecipeCardRow()
            Component.SEARCH_RESULT -> ShimmerMealPlannerRecipeCardColumn()
        }
    }

    @Composable
    fun ShimmerMealPlannerRecipeCardRow() {
        BoxWithConstraints {
            val boxWithConstraintsScope = this
            if (boxWithConstraintsScope.maxWidth >= 300.dp) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(Dimension.mealPlannerCardHeight)
                        .clip(RoundedCornerShape(10.dp))
                        .dashedBorder(2.dp, Colors.primary, 10.dp)
                        .background(color = Color(0x88FFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    ProgressIndicatorU(
                        progressIndicatorSize = 40.dp,
                        progressIndicatorColor = Colors.primary,
                        borderStroke = 2.dp
                    )
                }
            } else ShimmerMealPlannerRecipeCardColumn()
        }
    }

    @Composable
    fun ShimmerMealPlannerRecipeCardColumn() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(344.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape( 10.dp))
                .dashedBorder(2.dp, Colors.primary, 10.dp)
                .background(color = Color(0x88FFFFFF)),
            contentAlignment = Alignment.Center
        ) {
            ProgressIndicatorU(progressIndicatorSize = 40.dp, progressIndicatorColor = Colors.primary, borderStroke = 2.dp)
        }
    }

}

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(28f, 28f), -12f)
                    )
                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx)
                    )
                }
            }
        )
    }
)

@Composable
fun ProgressIndicatorU(progressIndicatorSize: Dp, progressIndicatorColor: Color, borderStroke: Dp) {
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
                borderStroke,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White,
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = borderStroke,
        color = Color.White
    )
}