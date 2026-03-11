package tech.miam.coursesuui.template.recipeCard

import ai.mealz.core.helpers.formatPrice
import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.components.baseComponent.likeButton.LikeButton
import ai.mealz.sdk.components.recipeCard.success.shelf.RecipeCardSuccessShelf
import ai.mealz.sdk.components.recipeCard.success.shelf.RecipeCardSuccessShelfParams
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.ressource.Image.discount
import ai.mealz.sdk.ressource.Image.miamGuest
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Colors.white
import ai.mealz.sdk.theme.Dimension
import ai.mealz.sdk.theme.Dimension.xsSpacerHeight
import ai.mealz.sdk.theme.Typography.bodyBold
import ai.mealz.sdk.theme.Typography.bodySmall
import ai.mealz.sdk.theme.Typography.subtitleBold
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import tech.miam.coursesuui.R
import tech.miam.coursesuui.theme.Typography

class StandaloneCoursesURecipeCard : RecipeCardSuccessShelf {
    val height = 200.dp

    @Composable
    override fun Content(params: RecipeCardSuccessShelfParams) {

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

                    RecipeCardImageView(
                        params.recipePicture,
                        params.sponsorLogo,
                        params.isADrink,
                        height,
                        Alignment.BottomStart
                    ) { params.goToDetail() }

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
                    RecipeCardTitleView(
                        params.recipeTitle,
                        Modifier
                            .weight(1f)
                            .padding(12.dp)
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(start = 12.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            Modifier.height(80.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Column {
                                if (params.productDiscountCount >= 0) {
                                    CoursesURecipeCardDiscountTag()
                                    Spacer(Modifier.height(xsSpacerHeight))
                                }
                                PricePerPerson(
                                    price = params.pricePerServe,
                                    sameLine = true
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                RecipeCardCTAView(
                                    params.mealzRecipeId,
                                    params.isInCart
                                ) {
                                    params.goToDetail()
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
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight(700), lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.mealz_mullish))
            ),
            color = Colors.black,
            modifier = modifier
        )
    }

    @Composable
    fun RecipeCardImageView(
        recipePicture: String,
        sponsorLogo: String?,
        isADrink: Boolean = false,
        height: Dp = 225.dp,
        sponsorAlignment: Alignment = Alignment.TopEnd,
        goToDetail: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = recipePicture,
                contentDescription = "Recipe Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth()
                    .clickable { goToDetail() }
            )
            Image(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart),
                painter = painterResource(id = if (isADrink) R.drawable.drink_flap else R.drawable.ic_meal_idea),
                contentDescription = "meal Idea"
            )
            Box(
                modifier = Modifier
                    .height(height)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            0f,  // TODO: set start
                            900f,
                        )
                    )
            ) {
            }
            SponsorLogo(sponsorLogo, Modifier.align(sponsorAlignment))
        }
    }

    @Composable
    fun SponsorLogo(sponsorLogo: String?, modifier: Modifier) {
        if (sponsorLogo != null) {
            Surface(
                shape = RoundedCornerShape(100.dp),
                color = Color.White,
                elevation = 1.dp,
                modifier = modifier.padding(8.dp)
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
    fun RecipeCardCTAView(
        recipeId: String,
        isInCart: Boolean,
        actionOnClick: () -> Unit
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LikeButton(recipeId = recipeId).Content()
            Spacer(modifier = Modifier.width(4.dp))
            Box {
                Surface(
                    shape = CircleShape,
                    color = if (isInCart) Color.Transparent else Colors.primary,
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
    }

    @Composable
    fun PricePerPerson(price: Double, sameLine: Boolean = false) {
        val formattedPrice = price.formatPrice()

        if (sameLine) {
            Row(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = formattedPrice,
                    style = subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    color = Colors.black
                )
                Text(
                    text = Localisation.myMeals.perPerson.localised,
                    style = bodySmall.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                    textAlign = TextAlign.Left,
                    color = Colors.grey
                )
            }
        } else {
            Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text(
                    text = formattedPrice,
                    style = subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    color = Colors.black
                )
                Text(
                    text = Localisation.myMeals.perPerson.localised,
                    style = bodySmall.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                    textAlign = TextAlign.Left,
                    color = Colors.grey
                )
            }
        }
    }

    @Composable
    internal fun BadgeViewGuest(numberOfGuests: MutableStateFlow<Int>) {

        val numberOfGuestsState by numberOfGuests.collectAsState()
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Surface(shape = RoundedCornerShape(100.dp), color = Color.White) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        numberOfGuestsState.toString(),
                        style = bodyBold.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        ).copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                    )
                    Icon(
                        painter = painterResource(id = miamGuest),
                        contentDescription = "guests icon",
                        Modifier
                            .size(16.dp)
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}