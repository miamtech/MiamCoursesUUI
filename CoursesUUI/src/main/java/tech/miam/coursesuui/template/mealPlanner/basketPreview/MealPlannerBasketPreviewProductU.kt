package tech.miam.coursesuui.template.mealPlanner.basketPreview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.kmm_miam_sdk.android.ui.components.counter.CounterColor
import com.miam.kmm_miam_sdk.android.ui.components.counter.CounterStyle
import com.miam.kmm_miam_sdk.android.ui.components.price.formatPrice
import com.miam.sdk.templateInterfaces.mealPlanner.basketPreview.MealPlannerBasketPreviewProduct
import com.miam.sdk.templateParameters.mealPlanner.basketPreview.MealPlannerBasketPreviewProductParameters
import tech.miam.coursesuui.component.CounterButton
import tech.miam.coursesuui.R

class CoursesUBasketPreviewProductImp: MealPlannerBasketPreviewProduct {

    @Composable
    override fun Content(mealPlannerBasketPreviewProductParameters: MealPlannerBasketPreviewProductParameters) {
        Column {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(Dimension.lPadding)
                    .height(185.dp)
            ) {
                val backgroundImage: Painter = rememberImagePainter(mealPlannerBasketPreviewProductParameters.picture)
                Image(
                    painter = backgroundImage,
                    contentDescription = "Grocery Item Image",
                    modifier = Modifier
                        .width(50.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (mealPlannerBasketPreviewProductParameters.sharedRecipeCount > 0) {
                        UtilizedInManyRecipes(mealPlannerBasketPreviewProductParameters.sharedRecipeCount)
                    }
                    Text(
                        text = mealPlannerBasketPreviewProductParameters.name,
                        color = Color.Black,
                        style = Typography.bodyBold
                    )
                    Text(
                        text = mealPlannerBasketPreviewProductParameters.description,
                        color = Color.Gray,
                        style = Typography.body
                    )
                    if (mealPlannerBasketPreviewProductParameters.isSubstitutable) {
                        Text(
                            text = "Changer d'article",
                            color = colorResource(R.color.miam_courses_u_primary),
                            style = Typography.link.copy(textDecoration = TextDecoration.Underline),
                            modifier = Modifier
                                .clickable { mealPlannerBasketPreviewProductParameters.changeProduct() }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = formatPrice(mealPlannerBasketPreviewProductParameters.price),
                            color = Color.Black,
                            style = Typography.subtitleBold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CounterForProduct(
                            initialCount = mealPlannerBasketPreviewProductParameters.quantity,
                            isDisable = false,
                            onCounterChanged = { count -> mealPlannerBasketPreviewProductParameters.onQuantityChanged(count) })
                        Image(
                            painter = painterResource(id = Image.delete),
                            contentDescription = "Trash Icon",
                            modifier = Modifier
                                .size(Dimension.lButtonHeight)
                                .padding(Dimension.sPadding)
                                .clickable {
                                    mealPlannerBasketPreviewProductParameters.delete()
                                }
                        )
                    }
                }
            }
            Divider()
        }
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
                .size(Dimension.lButtonHeight)
                .padding(Dimension.mPadding)
        )
        Text(
            text = "Utilisé dans $recipesUsedIn repas",
            color = colorResource(R.color.miam_courses_u_text_gray),
            style = Typography.bodySmall,
            modifier = Modifier

        )
    }
}

@Composable
fun CounterForProduct(
    initialCount: Int?,
    isDisable: Boolean,
    onCounterChanged: (newValue: Int) -> Unit,
    minValue: Int? = null,
    maxValue: Int? = null,
    isLoading: Boolean = false,
    key: Any? = null
) {
    var localCount by remember(key ?: initialCount) { mutableStateOf(initialCount) }

    fun newValueBounded(newValue: Int): Boolean {
        return (minValue == null || newValue >= minValue) && (maxValue == null || newValue <= maxValue)
    }

    fun changedValue(localCount: Int?, delta: Int): Int? {
        // if min value is not defined 1 is surely bounded
        if (localCount == null) return minValue ?: 1

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
            maxValue != null && localCount != null && localCount!! >= maxValue -> true
            else -> false
        }
    }

    fun minusDisabled(): Boolean {
        return when {
            isDisable -> true
            minValue != null && localCount != null && localCount!! <= minValue -> true
            else -> false
        }
    }

    Row(
        modifier = CounterStyle.mainRowContainer
            .clip(RoundedCornerShape(size = 50.dp))
            .background(colorResource(R.color.miam_courses_u_background_gray_light))
            .border(
                width = 0.5.dp, color = colorResource(R.color.miam_courses_u_background_gray),
                shape = RoundedCornerShape(size = 50.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CounterButton(
                Icons.Default.Remove,
                !minusDisabled(),
                if (minusDisabled()) Color.Gray else Colors.primary
            ) {
                decrease()
            }
            MiddleText(localCount, isLoading)
            CounterButton(
                Icons.Default.Add,
                !plusDisabled(),
                if (plusDisabled()) Color.Gray else Colors.primary
            ) {
                increase()
            }
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
            CircularProgressIndicator(color = CounterColor.countTextColor)
        } else {
            Text(
                text = counterText(localCount),
                color = Color.Black,
                style = Typography.bodyBold,
                modifier = Modifier.width(20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
fun MealPlannerBasketPreviewProductImpPreview() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CoursesUBasketPreviewProductImp().Content(
            mealPlannerBasketPreviewProductParameters = MealPlannerBasketPreviewProductParameters(
                id = "12",
                picture = "https://picsum.photos/200/300",
                name = "Tom's Hot Sauce",
                description = "Tom's Russian Special Spicy Hot Hot Sauce",
                price = 12.4,
                sharedRecipeCount = 3,
                isSubstitutable = true,
                isLoading = false,
                quantity = 1,
                onQuantityChanged = {},
                delete = { println("delete") },
                changeProduct = { println("change product") },
            )
        )
    }
}