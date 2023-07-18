package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import tech.miam.coursesuui.theme.Typography
import com.miam.core.sdk.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.ui.components.likeButton.LikeButton
import com.miam.kmm_miam_sdk.android.ui.components.price.SimplePrice
import com.miam.sdk.templateParameters.mealPlanner.recipe.MealPlannerRecipeCardParameters
import kotlin.math.roundToInt

@Composable
fun RecipeCardMealsList(params: MealPlannerRecipeCardParameters) {
    SwipeToDelete(params.deleteAction) {
        RecipeCardRow(params)
    }
}

@Composable
fun SwipeToDelete(deleteAction: () -> Unit, Overlay: @Composable () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    val threshold = 200f
    val maxSwipeDistance = 250f // Change this value to adjust the maximum swipe distance
    val showCloseIcon = remember { mutableStateOf(false) }

    Box(Modifier.padding(vertical = 8.dp)) {
        Button(
            onClick = {
                deleteAction()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Colors.miamDangerBackground
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .height(Dimension.mealPlannerCardHeight)
                .width(maxSwipeDistance.dp)
                .padding(end = 8.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Image.delete),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Colors.miamDangerText),
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .height(Dimension.mealPlannerCardHeight)
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        val newOffsetX = offsetX + dragAmount
                        if (newOffsetX <= 0f && newOffsetX >= -maxSwipeDistance) {
                            offsetX = newOffsetX
                        }
                        change.consume()
                        showCloseIcon.value = offsetX <= -threshold
                    }
                }
        ) {
            Overlay()
        }
    }
}

@Composable
fun RecipeCardRow(params: MealPlannerRecipeCardParameters) {

    val backgroundImage: Painter = rememberImagePainter(params.recipe.attributes?.mediaUrl)
    val likeButton =
        LikeButton().apply { bind(params.recipe.id) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(Dimension.mealPlannerCardHeight)
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Row(Modifier.background(Color.White)) {
            Box {
                Image(
                    painter = backgroundImage, contentDescription = null,
                    modifier = Modifier
                        .height(Dimension.mealPlannerCardHeight)
                        .width(150.dp)
                        .clickable {
                            params.openDetail()
                        },
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.offset(8.dp, 8.dp)) {
                    likeButton.Content()
                }
            }
            Column(Modifier.padding(horizontal = 16.dp)) {
                Text(
                    params.recipe.attributes?.title ?: "No Attributes",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .defaultMinSize(minHeight = 32.dp),
                    style = Typography.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RecipeCardMetric(text = params.recipe.totalTime, image = Image.time)
                    Divider(
                        thickness = 1.dp, color = Color.LightGray, modifier = Modifier
                            .height(32.dp)
                            .width(1.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        when (params.recipe.attributes!!.difficulty) {
                            1 -> RecipeCardMetric(
                                Localisation.Recipe.lowDifficulty.localised,
                                Image.difficultyLow,

                                )

                            2 -> RecipeCardMetric(
                                Localisation.Recipe.mediumDifficulty.localised,
                                Image.difficultyMid,

                                )

                            3 -> RecipeCardMetric(
                                Localisation.Recipe.highDifficulty.localised,
                                Image.difficultyHard,

                                )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color(0xFFD4F8DD))
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimplePrice(params.price) {
                        RecipeCardPrice(price = it)
                    }
                }
                Divider(thickness = 1.dp, color = Color.LightGray)
                Spacer(modifier = Modifier.weight(1f))
                Row(Modifier.padding(bottom = 8.dp)) {
                    TextButton(onClick = { params.changeAction() }) {
                        Icon(
                            painter = painterResource(id = Image.swap),
                            contentDescription = null,
                            tint = Colors.primary,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = Localisation.Basket.swapProduct.localised,
                            style = Typography.title.copy(fontWeight = FontWeight.Normal),
                            color = Colors.primary
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    IconButton(onClick = { params.deleteAction() }) {
                        Icon(painter = painterResource(Image.delete), contentDescription = null)
                    }
                }
            }
        }
    }
}