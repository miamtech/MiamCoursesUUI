package tech.miam.coursesuui.template.recipeDetail.loading

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.components.recipeDetail.success.product.loading.ProductLoadingParameters
import ai.mealz.sdk.components.recipeDetail.success.product.loading.ProductSuccess

class CoursesUProductLoading: ProductSuccess {
    @Composable
    override fun Content(params: ProductLoadingParameters) {
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Colors.backgroundLightGrey),
            contentAlignment = Alignment.TopStart,
        ) {
            Column() {
                Row(
                    Modifier
                        .fillMaxWidth(),
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(brush = brush)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 1.dp)
                            .height(80.dp)
                            .width(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(brush = brush)
                    )
                    Column(horizontalAlignment = Alignment.Start) {
                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                                .width(100.dp)
                                .clip(RoundedCornerShape(100))
                                .background(brush = brush)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .height(20.dp)
                                .width(200.dp)
                                .clip(RoundedCornerShape(100))
                                .background(brush = brush)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .height(25.dp)
                                .width(50.dp)
                                .clip(RoundedCornerShape(100))
                                .background(brush = brush)
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .height(25.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(100))
                                .background(brush = brush)
                        )
                    }

                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(25.dp)
                            .width(60.dp)
                            .clip(RoundedCornerShape(100))
                            .background(brush = brush)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(32.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(100))
                            .background(brush = brush)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(48.dp)
                            .width(48.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(brush = brush)
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewProductLoading() {
        // Provide sample data for your ProductEmptyParameters
        val sampleParams = ProductLoadingParameters
        Content(params = sampleParams)
    }
}