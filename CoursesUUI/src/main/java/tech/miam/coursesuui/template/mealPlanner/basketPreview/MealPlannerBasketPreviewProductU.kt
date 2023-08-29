package tech.miam.coursesuui.template.mealPlanner.basketPreview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.ui.components.counter.CounterStyle
import com.miam.kmm_miam_sdk.android.ui.components.price.formatPrice
import com.miam.sdk.templateInterfaces.mealPlanner.basketPreview.MealPlannerBasketPreviewProduct
import com.miam.sdk.templateParameters.mealPlanner.basketPreview.MealPlannerBasketPreviewProductParameters
import kotlinx.coroutines.flow.Flow
import tech.miam.coursesuui.component.CounterButton
import tech.miam.coursesuui.R
import tech.miam.coursesuui.template.mealPlanner.recipeCard.ProgressIndicatorU
import java.util.Locale

class CoursesUBasketPreviewProductImp : MealPlannerBasketPreviewProduct {

    @Composable
    override fun Content(mealPlannerBasketPreviewProductParameters: MealPlannerBasketPreviewProductParameters) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(15.dp)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                val backgroundImage: Painter =
                    rememberImagePainter(mealPlannerBasketPreviewProductParameters.picture)
                Image(
                    painter = backgroundImage, contentDescription = null, modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    if (mealPlannerBasketPreviewProductParameters.sharedRecipeCount > 1) {
                        UtilizedInManyRecipes(mealPlannerBasketPreviewProductParameters.sharedRecipeCount)
                    }
                    Text(
                        text = mealPlannerBasketPreviewProductParameters.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        },
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                        )
                    )
                    Text(
                        text = mealPlannerBasketPreviewProductParameters.description,
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF797978),
                        )
                    )
                    if (mealPlannerBasketPreviewProductParameters.isSubstitutable) {
                        Text(
                            text = "Changer d'article",
                            color = colorResource(R.color.miam_courses_u_primary),
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF007D8F),
                                textDecoration = TextDecoration.Underline,
                            ),
                            modifier = Modifier.clickable { mealPlannerBasketPreviewProductParameters.changeProduct() }
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 62.dp, end = 5.dp)
            ) {
                Text(
                    text = formatPrice(mealPlannerBasketPreviewProductParameters.price),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                CounterForProduct(
                    key = mealPlannerBasketPreviewProductParameters.id,
                    isLoading = mealPlannerBasketPreviewProductParameters.isLoading,
                    initialCount = mealPlannerBasketPreviewProductParameters.quantity,
                    isDisable = false,
                    onCounterChanged = { count ->
                        mealPlannerBasketPreviewProductParameters.onQuantityChanged(
                            count
                        )
                    }
                )
                IconButton(
                    onClick = {
                        mealPlannerBasketPreviewProductParameters.delete()
                    }
                ) {
                    Image(
                        painter = painterResource(id = Image.delete),
                        contentDescription = "Trash Icon",
                        modifier = Modifier
                            .size(28.dp)
                            .padding(Dimension.sPadding)
                    )
                }
            }
        }
        Divider(Modifier.padding(horizontal = 15.dp))
    }
}

@Composable
fun UtilizedInManyRecipes(recipesUsedIn: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(Dimension.sRoundedCorner))
            .background(colorResource(R.color.miam_courses_u_background_gray))
            .padding(horizontal = Dimension.sPadding)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.fork_cross_spoon),
            contentDescription = "Fork & Spoon Icon",
            modifier = Modifier
                .size(Dimension.mButtonHeight)
                .padding(1.dp)
        )
        Text(
            text = "Utilisé dans $recipesUsedIn repas",
            style = TextStyle(
                fontSize = 11.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF575756),
            ),
        )
    }
}

@Composable
fun CounterForProduct(
    initialCount: Int?,
    isDisable: Boolean,
    onCounterChanged: (newValue: Int) -> Unit,
    isLoading: Flow<Boolean>,
    key: Any? = null
) {
    var localCount by remember("$key$initialCount") { mutableStateOf(initialCount) }
    val isLoadingState by isLoading.collectAsState(initial = false)

    fun newValueBounded(newValue: Int): Boolean {
        return (newValue >= 0)
    }

    fun changedValue(localCount: Int?, delta: Int): Int? {
        // if min value is not defined 1 is surely bounded
        if (localCount == null) return 0

        if (!newValueBounded(localCount + delta)) return null

        return localCount + delta
    }

    fun increase() {
        changedValue(localCount, 1)?.let { newCount ->
            localCount = newCount
            onCounterChanged(newCount)
        }
    }

    fun decrease() {
        changedValue(localCount, -1)?.let { newCount ->
            localCount = newCount
            onCounterChanged(newCount)
        }
    }

    fun plusDisabled(): Boolean {
        return when {
            isDisable -> true
            else -> false
        }
    }

    fun minusDisabled(): Boolean {
        return when {
            isDisable -> true
            localCount != null && localCount!! <= 0 -> true
            else -> false
        }
    }
    Row(
        modifier = CounterStyle.mainRowContainer
            .border(
                width = 1.dp,
                color = Color(0xFFDDDDDD),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .height(40.dp)
            .background(
                color = colorResource(R.color.miam_courses_u_background_gray_light),
                shape = RoundedCornerShape(size = 20.dp)
            ),
        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        //.border(
        //                width = 0.5.dp, color = colorResource(R.color.miam_courses_u_background_gray),
        //                shape = RoundedCornerShape(size = 50.dp)
        //            )
    ) {
        CounterButton(
            Icons.Default.Remove,
            enable = !minusDisabled() && !isLoadingState,
            if (minusDisabled()) Color.Gray else Colors.primary,
            modifier = Modifier.size(30.dp).padding(1.dp),
        ) {
            decrease()
        }
        MiddleText(localCount, isLoadingState)
        CounterButton(
            Icons.Default.Add,
            enable = !plusDisabled() && !isLoadingState,
            if (plusDisabled()) Color.Gray else Colors.primary,
            modifier = Modifier.size(30.dp).padding(1.dp),
        ) {
            increase()
        }
    }
}

@Composable
fun MiddleText(localCount: Int?, isLoading: Boolean) {
    fun counterText(countValue: Int?): String {
        if (countValue == null) return "-"
        return localCount.toString()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (isLoading) {
            ProgressIndicatorU(
                progressIndicatorSize = 20.dp,
                progressIndicatorColor = Colors.primary,
                borderStroke = 1.dp
            )
        } else {
            Text(
                text = counterText(localCount),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

