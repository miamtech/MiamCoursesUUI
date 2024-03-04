package tech.miam.coursesuui.template.recipeDetail.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.sdk.components.baseComponent.counter.CounterParameters
import com.miam.sdk.components.baseComponent.likeButton.LikeButton
import com.miam.sdk.components.recipeDetail.success.info.RecipeDetailInfo
import com.miam.sdk.components.recipeDetail.success.info.RecipeDetailInfoParameters
import com.miam.sdk.di.TemplateDI

class CoursesURecipeDetailInfo: RecipeDetailInfo {
    @Composable
    override fun Content(params: RecipeDetailInfoParameters) {
        params.recipe.attributes?.let { attributes ->
            Box {
                AsyncImage(
                    model = attributes.mediaUrl,
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(280.dp)
                        .fillMaxWidth()
                )
                if (params.isLikeEnable) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Surface(shape = CircleShape,
                                color = Colors.white ,
                                modifier = Modifier.size(36.dp)
                            ) {
                            }
                            LikeButton(RectangleShape, recipeId = params.recipe.id).Content()
                        }
                    }
                }
                if (params.showGuestCounter) {
                    Box(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {

                     TemplateDI.defaultViews.counter?.view?.Content(
                            params = CounterParameters(
                                initialCount = params.guestCount,
                                isDisable = params.isUpdating,
                                isLoading = params.isUpdating,
                                onCounterChanged = { counterValue -> params.updateGuest(counterValue) },
                                minValue = 1,
                                maxValue = 99
                            )
                        )
                    }
                }
            }
        }
    }
}