package tech.miam.coursesuui.template.recipeCard

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.miam.sdk.components.recipeCard.loading.RecipeCardLoading

class CoursesURecipeCardLoading: RecipeCardLoading {

    @Composable
    override fun Content() {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6F),
            Color.LightGray.copy(alpha = 0.2F),
            Color.LightGray.copy(alpha = 0.6F)
        )
        val transition = rememberInfiniteTransition()
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutLinearInEasing
                )
            )
        )
        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(
                x = translateAnimation.value,
                y = translateAnimation.value
            )
        )

        BoxWithConstraints {
            if(maxWidth < 300.dp) {
                Card(modifier = Modifier.fillMaxWidth() ) {
                    ShimmerRecipeCard(brush,330, 260)
                }
            } else {
                Card(modifier = Modifier.fillMaxWidth() ) {
                    ShimmerRecipeCard(brush,300, 225)
                }
            }
        }
    }
}

@Composable
fun ShimmerRecipeCard(brush: Brush, cardHeight: Int, imageHeight: Int) {
    Column(Modifier.height(cardHeight.dp).fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .height(imageHeight.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(brush = brush)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(14.dp)
                        .width(80.dp)
                        .clip(RoundedCornerShape(100))
                        .background(brush = brush)
                )
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(8.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(100))
                        .background(brush = brush)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Spacer(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(32.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(100))
                    .background(brush = brush)
            )
        }
    }
}