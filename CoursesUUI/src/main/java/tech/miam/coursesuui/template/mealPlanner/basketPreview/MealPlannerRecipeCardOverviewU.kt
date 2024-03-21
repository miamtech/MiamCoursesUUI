package tech.miam.coursesuui.template.mealPlanner.basketPreview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import ai.mealz.core.localisation.Localisation
import tech.miam.coursesuui.template.mealPlanner.recipeCard.ProgressIndicatorU
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Dimension
import ai.mealz.sdk.theme.Typography
import ai.mealz.sdk.theme.Typography.body
import ai.mealz.sdk.components.baseComponent.likeButton.LikeButton
import ai.mealz.sdk.components.mealPlanner.basketPreview.success.recipeRow.MealPlannerBasketPreviewSuccessRecipeRow
import ai.mealz.sdk.components.mealPlanner.basketPreview.success.recipeRow.MealPlannerBasketPreviewSuccessRecipeRowParameters
import java.text.NumberFormat
import tech.miam.coursesuui.R
import java.util.*

class RecipeCardOverview: MealPlannerBasketPreviewSuccessRecipeRow {
    @Composable
    override fun Content(params: MealPlannerBasketPreviewSuccessRecipeRowParameters) {

        val likeButton = remember {
            LikeButton(recipeId = params.recipeId)
        }

        val isLocked = params.isLock.collectAsState()

        val mealPlannerBasketPreviewRecipeOverviewParametersState by remember(
                params.name +
                    "${params.isDeleting}" +
                    "${params.isPriceRefreshing}" +
                    "${params.price}" +
                    "${params.numberOfGuest}"

        ) {
            mutableStateOf(params)
        }

        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(10.dp)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Row(modifier = Modifier.height(184.dp)) {
                        Box {
                            AsyncImage(
                                model = mealPlannerBasketPreviewRecipeOverviewParametersState.picture,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(Dimension.mealPlannerCardHeight)
                                    .width(150.dp)
                                    .clickable { mealPlannerBasketPreviewRecipeOverviewParametersState.openRecipeDetail() },
                                contentScale = ContentScale.Crop
                            )
                            Box(modifier = Modifier.offset(8.dp, 8.dp)) {
                                likeButton.Content()
                            }
                        }
                        Column(Modifier.padding(horizontal = 16.dp)) {
                            Text(
                                mealPlannerBasketPreviewRecipeOverviewParametersState.name,
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .defaultMinSize(minHeight = 40.dp)
                                    .clickable { params.openRecipeDetail() },
                                style = Typography.subtitleBold.copy(fontSize = 14.sp),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                                Text(
                                    text = Localisation.Recipe.numberOfIngredients(mealPlannerBasketPreviewRecipeOverviewParametersState.productCount).localised,
                                    style = body.copy(fontSize = 12.sp)
                                )
                            SuccessViewPerGuest(
                                mealPlannerBasketPreviewRecipeOverviewParametersState.price / mealPlannerBasketPreviewRecipeOverviewParametersState.numberOfGuest,
                            )
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth()
                                    .height(36.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(color = Color(0xFFD4F8DD))
                                        .width(76.dp)
                                        .height(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (params.isChildrenLoading || params.isPriceRefreshing || isLocked.value){
                                        ProgressIndicatorU(progressIndicatorSize = 20.dp, progressIndicatorColor = Colors.primary, borderStroke = 1.dp)
                                    } else {
                                        PriceView(mealPlannerBasketPreviewRecipeOverviewParametersState.price)
                                    }
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                )
                                GuestNumberComponent(
                                    mealPlannerBasketPreviewRecipeOverviewParametersState.numberOfGuest,
                                    isLoading = isLocked.value
                                ) { guestValue ->
                                    mealPlannerBasketPreviewRecipeOverviewParametersState.onGuestChange(guestValue)
                                }
                            }
                            Divider(thickness = 1.dp, color = Color.LightGray)
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CollapseButton(initaleState = mealPlannerBasketPreviewRecipeOverviewParametersState.isExpanded,
                                    isLoading = params.isChildrenLoading || params.isPriceRefreshing || isLocked.value) {
                                    mealPlannerBasketPreviewRecipeOverviewParametersState.toggleCollapse()
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                )
                                if (mealPlannerBasketPreviewRecipeOverviewParametersState.isDeleting) {
                                    ProgressIndicatorU(progressIndicatorSize = 20.dp, progressIndicatorColor = Colors.primary, borderStroke = 1.dp)
                                } else {
                                    IconButton(
                                        onClick = { mealPlannerBasketPreviewRecipeOverviewParametersState.delete() },
                                        enabled = !params.isDeleting && !isLocked.value && !params.isChildrenLoading
                                    ) {
                                        Icon(painter = painterResource(Image.delete), contentDescription = null)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PriceView(price: Double) {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.currency = Currency.getInstance(Localisation.Price.currency.localised)
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_underline),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomStart),
                contentScale = ContentScale.Crop
            )
            Text(
                text = numberFormat.format(price),
                style = Typography.subtitleBold.copy(fontSize = 14.sp)
            )
        }
    }
}

@Composable
fun SuccessViewPerGuest(price: Double ) {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.currency = Currency.getInstance(Localisation.Price.currency.localised)
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Text(
                text = numberFormat.format(price),
                style = body.copy(fontSize = 12.sp)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text("/ personne", style = body.copy(fontSize = 12.sp))
    }
}

@Composable
fun GuestNumberComponent(initialValue: Int,isLoading: Boolean, onGuestChange: (Int) -> Unit ) {
    var guestNumber by remember { mutableStateOf(initialValue) }
    Row {
        IconButton(onClick = {
            if (guestNumber > 1) {
                guestNumber--
                onGuestChange(guestNumber)
            }
        },
            enabled = !isLoading,
            modifier = Modifier.size(30.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_minus_u),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Colors.primary
            )
        }
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = guestNumber.toString(), style = Typography.bodyBold.copy(fontSize = 14.sp))
            Text(text = "pers.", style = body.copy(fontSize = 14.sp))
        }
        IconButton(onClick = {
            if (guestNumber < 100) {
                guestNumber++
                onGuestChange(guestNumber)
            }
        },   enabled = !isLoading,
            modifier = Modifier.size(30.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus_u),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Colors.primary
            )
        }
    }
}

@Composable
private fun CollapseButton(initaleState: Boolean, isLoading: Boolean, action: () -> Unit) {
    var expandedState by remember { mutableStateOf(initaleState) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    TextButton(onClick = {
        action()
        expandedState = !expandedState
    }, enabled = !isLoading) {
        Text(
            text = Localisation.Basket.moreDetails.localised,
            style = Typography.subtitle.copy(fontSize = 14.sp),
            color = Colors.primary
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron),
            contentDescription = null,
            tint = Colors.primary,
            modifier = Modifier
                .padding(start = 4.dp)
                .rotate(rotationState)
        )
    }
}