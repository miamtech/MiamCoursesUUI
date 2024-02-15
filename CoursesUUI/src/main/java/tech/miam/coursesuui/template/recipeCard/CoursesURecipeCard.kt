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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

class CoursesURecipeCard: RecipeCardSuccess {
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
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    Column {
                        Box {
                            params.recipe.attributes?.let {
                                RecipeCardImageView(it.mediaUrl) { params.goToDetail() }
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopStart),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (params.isSponsor) {
                                    SponsorLogo(params.sponsorLogo)
                                } else {
                                    Spacer(Modifier.weight(1f))
                                }
                                if (params.likeIsEnabled) {
                                    RecipeLikeButton(params.recipe.id)
                                }
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomStart)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                RecipeCardTitleView(params.recipeTitle, Modifier.weight(1f))
                                BadgeViewGuest(params.guest)
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ;
                            Box(modifier = Modifier.width(70.dp)) {
                                PricePerPerson(params.recipe.attributes?.price?.pricePerServe ?: 0.0)
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                RecipeCardCTAView(params.isInCart) {
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
fun RecipeCardImageView(recipePicture: String, goToDetail: () -> Unit) {
    Box(
        modifier = Modifier
            .height(225.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = recipePicture,
            contentDescription = "Recipe Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(225.dp)
                .fillMaxWidth()
                .clickable { goToDetail() }
        )
        Box(
            modifier = Modifier
                .height(225.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black,),
                        0f,  // TODO: set start
                        900f,
                    )
                )
        ) {
        }
    }
}

@Composable
fun SponsorLogo(sponsorLogo: String?) {
    if (sponsorLogo != null) {
        Surface(
            shape = RoundedCornerShape(100.dp),
            color = Colors.white,
            elevation = 1.dp,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = sponsorLogo,
                contentDescription = "sponsor picture",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .heightIn(0.dp, 40.dp)
                    .padding(2.dp)
            )
        }
    }
}

@Composable
fun RecipeCardTitleView(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        maxLines = 2,
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700), lineHeight = 24.sp),
        color = Colors.white,
        modifier = modifier
    )
}

@Composable
fun RecipeLikeButton(recipeId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        LikeButton(recipeId = recipeId).Content()
    }
}

@Composable
fun RecipeCardCTAView(
    isInCart: Boolean,
    actionOnClick: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    border = BorderStroke(
                        1.dp,
                        if (isInCart) Colors.primary else Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { actionOnClick() },
            elevation = 8.dp
        ) {
            Row(
                Modifier
                    .background(if (isInCart) Colors.white else Colors.primary)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(if (isInCart) Image.check else Image.cart),
                    contentDescription = "recipe is in cart icon",
                    colorFilter = ColorFilter.tint(if (isInCart) Colors.primary else Colors.white),
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(
                    text = if (isInCart) Localisation.Recipe.showDetails.localised else Localisation.Recipe.add.localised,
                    color = if (isInCart) Colors.primary else Colors.white,
                    style = Typography.bodyBold
                )
            }
        }
    }
}

@Composable
fun PricePerPerson(price: Double) {
    val formattedPrice = price.formatPrice()
    Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
        Text(
            text = formattedPrice,
            style = Typography.subtitleBold,
            textAlign = TextAlign.Left,
            maxLines = 2,
            color = Colors.black
        )
        Text(
            text = Localisation.MyMeals.perPerson.localised,
            style = Typography.bodySmall,
            textAlign = TextAlign.Left,
            color = Colors.grey
        )
    }
}

@Composable
internal fun BadgeViewGuest(numberOfGuests: MutableStateFlow<Int>) {

    val numberOfGuestsState by numberOfGuests.collectAsState()
    Row(
        Modifier.width(100.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Surface(shape = RoundedCornerShape(100.dp), color = Colors.white) {
            Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                Text(
                    numberOfGuestsState.toString(),
                    style = Typography.bodyBold.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    ),
                )
                Icon(
                    painter = painterResource(id = Image.miamGuest),
                    contentDescription = "guests icon",
                    Modifier
                        .size(24.dp)
                        .padding(start = 2.dp)
                )
            }
        }
    }
}