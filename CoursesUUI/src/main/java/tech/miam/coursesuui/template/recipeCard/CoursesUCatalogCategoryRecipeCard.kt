package tech.miam.coursesuui.template.recipeCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import ai.mealz.core.helpers.formatPrice
import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import ai.mealz.sdk.components.baseComponent.likeButton.LikeButton
import ai.mealz.sdk.components.recipeCard.success.RecipeCardSuccessParams
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CoursesUCatalogCategoryRecipeCard(params: RecipeCardSuccessParams) {

        Surface(
            border = BorderStroke(1.dp, Colors.lightgrey),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .height(330.dp)
                .width(240.dp)
        ) {
            Column {
                Box {
                    params.recipe.attributes?.let {
                        CatalogRecipeCardImageView(it.mediaUrl) {params.goToDetail() }
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (params.isSponsor) {
                            params.recipe.relationships?.sponsors?.data?.get(0)?.attributes?.logoUrl?.let {
                                CatalogSponsorLogo(it)
                            }
                        } else {
                            Spacer(Modifier.weight(1f))
                        }
                        CatalogRecipeLikeButton(params.recipe.id)
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        params.recipe.attributes?.title?.let {
                            CatalogRecipeCardTitleView(it, Modifier.weight(1f))
                        }
                        CatalogBadgeViewGuest(params.guest)
                    }
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(100.dp)) {
                        CatalogPricePerPerson(params.recipe.attributes?.price?.pricePerServe ?: 0.0)
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        CatalogRecipeCardCTAView(params.isInCart) {
                            params.goToDetail()
                        }
                    }
                }
            }
        }
}

@Composable
fun CatalogRecipeCardImageView(recipePicture: String, goToDetail: () -> Unit) {
    Box(
        modifier = Modifier
            .height(260.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = recipePicture,
            contentDescription = "Recipe Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(260.dp)
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
fun CatalogSponsorLogo(sponsorLogo: String?) {
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
fun CatalogRecipeCardTitleView(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        maxLines = 3,
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700), lineHeight = 24.sp),
        color = Colors.white,
        modifier = modifier
    )
}

@Composable
fun CatalogRecipeLikeButton(recipeId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Box {
            Surface(
                shape = CircleShape,
                color =  Colors.white ,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(36.dp)
            ) {}
            LikeButton(recipeId = recipeId).Content()
        }
    }
}

@Composable
fun CatalogRecipeCardCTAView(
    isInCart: Boolean,
    actionOnClick: () -> Unit
) {
    Box {
        Surface(
            shape = CircleShape,
            color = if(isInCart) Color.Transparent else Colors.primary ,
            modifier = Modifier
                .align(Alignment.Center)
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    border = BorderStroke(
                        1.dp,
                        if (isInCart) Colors.primary else Color.Transparent
                    ),
                    shape = CircleShape
                )
                .clickable { actionOnClick() }
        ) {}
        Image(
            painter = painterResource(if (isInCart) Image.check else Image.cart),
            contentDescription = "recipe is in cart icon",
            colorFilter = ColorFilter.tint(if (isInCart) Colors.primary else Colors.white),
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun CatalogPricePerPerson(price: Double) {
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
internal fun CatalogBadgeViewGuest(numberOfGuests: MutableStateFlow<Int>) {

    val numberOfGuestsState by numberOfGuests.collectAsState()
    Row(

        horizontalArrangement = Arrangement.End
    ) {
        Surface(shape = RoundedCornerShape(100.dp), color = Color.White) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical =2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        .size(16.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }
}