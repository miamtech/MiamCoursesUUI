package tech.miam.coursesuui.template.recipeDetail.ignore

import com.miam.sdk.components.recipeDetail.success.product.ignore.ProductIgnore
import com.miam.sdk.components.recipeDetail.success.product.ignore.ProductIgnoreParameters
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.core.viewModels.quantityFormatter.QuantityFormatter
import com.miam.kmm_miam_sdk.android.theme.Colors

class CoursesUIgnore: ProductIgnore {
    @Composable
    override fun Content(params: ProductIgnoreParameters) {

        val guestsCount = params.guestsCount.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, Colors.lightgrey, RoundedCornerShape(8.dp))
                .background(Colors.lightgrey)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ProductHeader(params.ingredientName, params.ingredientQuantity, params.ingredientUnit, guestsCount.value, params.defaultRecipeGuest)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = Localisation.Ingredient.willNotBeAdded.localised, style = TextStyle(
                            fontSize = 14.sp,
                            color = Colors.grey
                        )
                    )
                    Surface(shape = RoundedCornerShape(100.dp),
                        color = Colors.white
                    ) {
                        Text(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp).clickable {
                            params.chooseProduct()
                        },
                            text = Localisation.Ingredient.chooseProduct.localised,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight(700), color = Colors.primary)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ProductHeader(
        ingredientName: String,
        ingredientQuantity: String,
        ingredientUnit: String,
        guestsCount: Int,
        defaultRecipeGuest: Int
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = ingredientName.replaceFirstChar { it.titlecaseChar() },
                style = TextStyle(
                    fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight(900), color =
                    Colors.boldText
                )
            )

            Text(
                text = QuantityFormatter.readableFloatNumber(
                    value = QuantityFormatter.realQuantities(ingredientQuantity, guestsCount, defaultRecipeGuest),
                    unit = ingredientUnit
                ),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(500),
                    color = Colors.boldText
                )
            )
        }
    }
}