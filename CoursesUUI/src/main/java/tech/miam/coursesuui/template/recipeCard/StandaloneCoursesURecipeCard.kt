package tech.miam.coursesuui.template.recipeCard

import com.miam.sdk.components.recipeCard.success.RecipeCardSuccess
import com.miam.sdk.components.recipeCard.success.RecipeCardSuccessParams
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miam.core.helpers.formatPrice
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.components.baseComponent.likeButton.LikeButton
import kotlinx.coroutines.flow.MutableStateFlow
import tech.miam.coursesuui.R

class StandaloneCoursesURecipeCard: RecipeCardSuccess {
    val height = 200.dp
    @Composable
    override fun Content(params: RecipeCardSuccessParams) {
        BoxWithConstraints {
            if(maxWidth < 300.dp) {
                CoursesUCatalogCategoryRecipeCard(params)
            } else {
                Surface(
                    border = BorderStroke(1.dp, Colors.lightgrey),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(height)
                        .padding(8.dp)
                ) {
                    Row {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight()
                        ) {
                            params.recipe.attributes?.let {
                                RecipeCardImageView(it.mediaUrl, params.sponsorLogo, height, Alignment.BottomStart) { params.goToDetail() }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                BadgeViewGuest(params.guest)
                            }

                        }
                        Column {
                            RecipeCardTitleView(params.recipeTitle, Modifier.weight(1f).padding(12.dp))

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ;
                                Box(modifier = Modifier.width(70.dp)) {
                                    PricePerPerson(
                                        params.recipe.attributes?.price?.pricePerServe ?: 0.0
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    RecipeCardCTAView(params.recipe.id, params.isInCart) {
                                        params.goToDetail()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RecipeCardTitleView(title: String, modifier: Modifier = Modifier) {
        Text(
            text = title,
            maxLines = 2,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700), lineHeight = 24.sp),
            color = Colors.black,
            modifier = modifier
        )
    }
}