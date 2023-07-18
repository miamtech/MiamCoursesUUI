package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import tech.miam.coursesuui.theme.Typography
import com.miam.core.sdk.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.ui.components.price.SimplePrice
import com.miam.sdk.templateParameters.mealPlanner.recipe.MealPlannerRecipeCardParameters
import com.miam.sdk.ui.mealPlanner.recipe.BudgetLikeButton

@Composable
fun RecipeCardSearch(params: MealPlannerRecipeCardParameters) {
    val backgroundImage: Painter = rememberImagePainter(params.recipe.attributes?.mediaUrl)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(10.dp))
            .background(Color.White)
            .height(340.dp)
    ) {
        Column {
            Box(Modifier.clickable {
                params.openDetail()
            }) {
                Image(
                    painter = backgroundImage, contentDescription = null, modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                BudgetLikeButton(recipeId = params.recipe.id, sizeOfImage = 208.dp)
            }
            Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    "${(params.recipe.attributes?.title ?: "No Attributes")}\n",
                    style = Typography.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .height(35.dp)

                ) {
                    RecipeCardMetric(text = params.recipe.totalTime, image = Image.time)
                    Divider(
                        thickness = 1.dp, color = Color.LightGray, modifier = Modifier
                            .height(32.dp)
                            .width(1.dp)
                    )
                    Column {
                        when (params.recipe.attributes!!.difficulty) {
                            1 -> RecipeCardMetric(
                                Localisation.Recipe.lowDifficulty.localised,
                                Image.difficultyLow,

                                )

                            2 -> RecipeCardMetric(
                                Localisation.Recipe.mediumDifficulty.localised,
                                Image.difficultyMid,
                                Modifier.weight(1f)
                            )

                            3 -> RecipeCardMetric(
                                Localisation.Recipe.highDifficulty.localised,
                                Image.difficultyHard,
                                Modifier.weight(1f)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color(0xFFD4F8DD))
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimplePrice(params.recipe.attributes?.computedCost ?: 0.0) {
                        RecipeCardPrice(price = it)
                    }
                }
                Divider(thickness = 1.dp, color = Color.LightGray)
                Row(Modifier.padding(top = 8.dp)) {
                    Button(
                        onClick = { params.replaceAction() },
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(32.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primary)
                    ) {
                        Icon(
                            painter = painterResource(id = Image.plus),
                            contentDescription = null,
                            tint = Colors.white,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(16.dp)
                        )
                        Text(text = "Ajouter", style = Typography.title.copy(fontWeight = FontWeight.Normal), color = Colors.white)
                    }
                }
            }
        }
    }
}

