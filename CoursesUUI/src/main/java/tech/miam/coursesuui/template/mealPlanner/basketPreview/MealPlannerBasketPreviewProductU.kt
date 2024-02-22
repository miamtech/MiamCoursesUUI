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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Dimension
import com.miam.kmm_miam_sdk.android.theme.Dimension.sPadding
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.components.mealPlanner.basketPreview.success.found.products.FoundProduct
import com.miam.sdk.components.mealPlanner.basketPreview.success.found.products.FoundProductParameters
import com.miam.sdk.components.price.formatPrice
import tech.miam.coursesuui.component.CounterButton
import tech.miam.coursesuui.R
import tech.miam.coursesuui.template.mealPlanner.recipeCard.ProgressIndicatorU

class CoursesUBasketPreviewProductImp: FoundProduct {

    @Composable
    override fun Content(params: FoundProductParameters) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(Dimension.lPadding)
            ) {
                AsyncImage(
                    model = params.productPicture,
                    contentDescription = null,
                    modifier = Modifier
                    .height(60.dp)
                    .width(60.dp),
                    contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (params.shareInRecipeCount > 1) {
                        UtilizedInManyRecipes(params.shareInRecipeCount)
                    }
                    Text(
                        text = params.productName.capitalize(),
                        color = Color.Black,
                        style = Typography.bodyBold
                    )
                    Text(
                        text = "${params.productName} \n ${params.productCapacityUnit}",
                        color = Color.Gray,
                        style = Typography.bodySmall
                    )
                        Text(
                            text = "Changer d'article",
                            color = colorResource(R.color.miam_courses_u_primary),
                            style = Typography.link.copy(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Normal,),
                            modifier = Modifier.clickable { params.replace() }
                        )
                    Spacer(Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = params.price.formatPrice(),
                            color = Color.Black,
                            style = Typography.subtitleBold.copy( fontSize = 22.sp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CounterForProduct(
                            key = params.ingredientId ,
                            isLoading = params.isReloading,
                            initialCount = params.quantity,
                            isDisable = false,
                            onCounterChanged = { count -> params.onQuantityChanged(count) })
                        IconButton(
                            onClick = {
                                params.delete()
                            }
                        ) {
                        Image(
                            painter = painterResource(id = Image.delete),
                            contentDescription = "Trash Icon",
                            modifier = Modifier
                                .size(Dimension.lButtonHeight)
                                .padding(Dimension.sPadding)
                            )
                        }
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
            modifier = Modifier.padding(end = Dimension.mPadding)
        )
    }
}

@Composable
fun CounterForProduct(
    initialCount: Int?,
    isDisable: Boolean,
    onCounterChanged: (newValue: Int) -> Unit,
    isLoading: Boolean ,
    key: Any? = null
) {
    var localCount by remember("$key$initialCount") { mutableStateOf(initialCount) }


    fun newValueBounded(newValue: Int): Boolean {
        return ( newValue >= 0)
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
        modifier = Modifier.padding(
            horizontal = sPadding,
            vertical = sPadding
        ).height(50.dp)
            .clip(RoundedCornerShape(size = 50.dp))
            .background(colorResource(R.color.miam_courses_u_background_gray_light))
            .border(
                width = 0.5.dp, color = colorResource(R.color.miam_courses_u_background_gray),
                shape = RoundedCornerShape(size = 50.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(
            Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CounterButton(
                Icons.Default.Remove,
                enable= !minusDisabled() && !isLoading,
                if (minusDisabled()) Color.Gray else Colors.primary,
                modifier =Modifier.size(30.dp),
            ) {
                decrease()
            }
            MiddleText(localCount, isLoading)
            CounterButton(
                Icons.Default.Add,
                enable= !plusDisabled() && !isLoading,
                if (plusDisabled()) Color.Gray else Colors.primary,
                modifier = Modifier.size(30.dp),
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
            ProgressIndicatorU(progressIndicatorSize = 20.dp, progressIndicatorColor = Colors.primary, borderStroke = 1.dp)
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

