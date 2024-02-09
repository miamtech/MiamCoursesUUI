package tech.miam.coursesuui.template.myMeal

import com.miam.sdk.components.myMeal.myMealRecipeCard.success.MyMealRecipeCardSuccess
import com.miam.sdk.components.myMeal.myMealRecipeCard.success.MyMealRecipeCardSuccessParameters
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.miam.core.helpers.formatPrice
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.theme.Typography


class CoursesUMyMealRecipe(): MyMealRecipeCardSuccess {
    @Composable
    override fun Content(params: MyMealRecipeCardSuccessParameters) {
        Surface(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(
                    width = 1.dp,
                    color = Colors.disabledText,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                Modifier.padding(8.dp)
            )
            {
                RecipeImage(imageUrl = params.recipe.attributes?.mediaUrl ?: "", params.guestCount)
                Column(
                    Modifier.padding(start = 8.dp)
                ) {
                    Row {
                        Text(
                            text = params.recipe.attributes?.title ?: "",
                            style = Typography.subtitleBold.copy(lineBreak = LineBreak.Simple),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(130.dp),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        DeleteButton(params.isDeleting, params.delete)
                    }
                    Text(
                        text = Localisation.MyMeals.products(params.numberOfProductsInRecipe).localised,
                        style = Typography.bodySmall,
                        color = Colors.disabledText
                    )
                    PricePerPerson(
                        params.totalPrice,
                        params.guestCount
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        modifier = Modifier
                            .border(
                                2.dp,
                                color = Colors.primary,
                                shape = RoundedCornerShape(64.dp)
                            )
                            .fillMaxWidth(),
                        onClick = params.openRecipeDetail,
                    ) {
                        Row {
                            Text(
                                text = Localisation.Recipe.showBasketPreview.localised,
                                style = Typography.subtitle,
                                color = Colors.primary
                            )
                            Icon(
                                painter = painterResource(Image.previous),
                                contentDescription = "Icon arrow view products",
                                modifier = Modifier
                                    .graphicsLayer {
                                        translationY = 4.dp.toPx()
                                        rotationZ = 180f
                                    }
                                    .padding(top = 4.dp),
                                tint = Colors.primary
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    internal fun BadgeViewGuest(numberOfGuests: Int, modifier: Modifier = Modifier) {
        Surface(
            modifier = modifier,
        ) {
            Row (modifier = Modifier.padding(horizontal = 4.dp)) {
                Text(
                    numberOfGuests.toString(),
                    style = Typography.bodyBold,
                )
                Icon(
                    painter = painterResource(id = Image.guests),
                    contentDescription = "guests icon",
                    Modifier
                        .size(24.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }

    @Composable
    internal fun RecipeImage(imageUrl: String, numberOfGuests: Int) {
        Box {
            val backgroundImage: Painter = rememberImagePainter(imageUrl)
            Image(
                painter = backgroundImage,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .height(Dimension.myMealPictureCardSize)
                    .width(Dimension.myMealPictureCardSize)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            BadgeViewGuest(
                numberOfGuests = numberOfGuests,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Colors.white)
            )
        }
    }

    @Composable
    internal fun DeleteButton(isDeleting: Boolean, delete: () -> Unit) {
        IconButton(onClick = delete) {
            if (isDeleting) {
                CircularProgressIndicator(color = Colors.boldText,modifier= Modifier.size(16.dp))
            } else {
                Image(
                    painter = painterResource(Image.delete),
                    contentDescription = "Delete",
                    colorFilter = ColorFilter.tint(Colors.grey),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }

    @Composable
    internal fun PricePerPerson(price: Double, numberOfGuests: Int) {
        val pricePerPerson = if (numberOfGuests != 0) price / numberOfGuests else 0.0
        val formattedPrice = pricePerPerson.formatPrice()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(0.dp)
        ) {
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
}