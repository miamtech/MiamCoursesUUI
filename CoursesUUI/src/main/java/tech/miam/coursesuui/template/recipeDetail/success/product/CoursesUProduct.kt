package tech.miam.coursesuui.template.recipeDetail.success.product

import com.miam.sdk.components.recipeDetail.success.product.success.ProductSuccess
import com.miam.sdk.components.recipeDetail.success.product.success.ProductSuccessParameters
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.miam.core.localisation.Localisation
import com.miam.core.viewModels.quantityFormatter.QuantityFormatter
import com.miam.kmm_miam_sdk.android.ressource.Image.cart
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Colors.black
import com.miam.kmm_miam_sdk.android.theme.Colors.boldText
import com.miam.kmm_miam_sdk.android.theme.Colors.grey
import com.miam.kmm_miam_sdk.android.theme.Colors.lightgrey
import com.miam.kmm_miam_sdk.android.theme.Colors.primary
import com.miam.kmm_miam_sdk.android.theme.Colors.white
import com.miam.sdk.components.baseComponent.counter.CounterParameters
import com.miam.sdk.di.TemplateDI

class CoursesUProduct: ProductSuccess {
    @Composable
    override fun Content(params: ProductSuccessParameters) {

        val guestsCount = params.guestsCount.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(
                    1.dp,
                    if (params.isInBasket) primary else lightgrey, RoundedCornerShape(8.dp)
                ).clip(RoundedCornerShape(8.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                ProductHeader(
                    params.ingredientName,
                    params.ingredientQuantity,
                    params.ingredientUnit,
                    params.isInBasket,
                    guestsCount.value,
                    params.defaultRecipeGuest
                )
                ProductInformation(
                    params.productName,
                    params.productBrand,
                    params.productCapacityVolume,
                    params.productUnit,
                    params.productImage,
                    params.isSponsored,
                    params.replaceProduct
                )
                ActionRow(
                    params.formattedUnitPrice,
                    params.productQuantity,
                    params.isInBasket,
                    params.isLocked,
                    params.addProduct,
                    params.ignoreProduct,
                    params.updateProductQuantity
                )

                if (params.numberOfRecipeConcernsByProduct > 1 && params.isInBasket) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(lightgrey)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = Localisation.Ingredient.productsSharedRecipe(params.numberOfRecipeConcernsByProduct).localised,
                            style = TextStyle(fontSize = 12.sp, color = grey, fontWeight = FontWeight.Bold)
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
        isInBasket: Boolean,
        guest: Int,
        defaultRecipeGuest: Int
    ) {
        Row(
            Modifier
                .background(if (isInBasket) primary else lightgrey)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = ingredientName.replaceFirstChar { it.titlecaseChar() },
                style = TextStyle(
                    fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight(900), color =
                    if (isInBasket) white else boldText
                )
            )
            Text(
                text = QuantityFormatter.readableFloatNumber(
                    value = QuantityFormatter.realQuantities(ingredientQuantity, guest, defaultRecipeGuest),
                    unit = ingredientUnit
                ),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(400),
                    color = if (isInBasket) white else boldText
                )
            )
        }
    }

    @Composable
    private fun ProductInformation(
        productName: String,
        productBrand: String,
        productCapacityVolume: String,
        productUnit: String,
        productImage: String,
        isSponsor: Boolean,
        replaceProduct: () -> Unit

    ) {
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = productImage,
                contentDescription = "Product image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .padding(4.dp)
                    .fillMaxSize()
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = productBrand,
                    style = TextStyle(fontSize = 16.sp, lineHeight = 18.sp, fontWeight = FontWeight(700), color = Colors.boldText)
                )
                Text(
                    text = productName,
                    style = TextStyle(fontSize = 12.sp, lineHeight = 18.sp, fontWeight = FontWeight(400))
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(shape = RoundedCornerShape(100.dp), color = lightgrey) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            text = "$productCapacityVolume $productUnit",
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight(500), color = boldText)
                        )
                    }
                    if (isSponsor) {
                        Text(
                            text = Localisation.Basket.sponsored.localised,
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight(500), color = boldText),
                            modifier = Modifier
                                .background(Color.Transparent, shape = RoundedCornerShape(100.dp))
                                .border(BorderStroke(1.dp, lightgrey), shape = RoundedCornerShape(100.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }

                TextButton(onClick = { replaceProduct() }) {
                    Text(
                        text = Localisation.Budget.replaceItem.localised,
                        style = TextStyle(fontSize = 14.sp, lineHeight = 16.sp, fontWeight = FontWeight(600), color = primary)
                    )
                }
            }
        }
    }

    @Composable
    private fun ActionRow(
        productPrice: String,
        productQuantity: Int,
        isInBasket: Boolean,
        isLocked: Boolean,
        addProduct: () -> Unit,
        ignoreProduct: () -> Unit,
        changeCount: (Int) -> Unit
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = productPrice,
                style = TextStyle(fontSize = 20.sp, lineHeight = 24.sp, fontWeight = FontWeight(900), color = black)
            )
            if (isInBasket) {
                TemplateDI.recipeDetail.success.product.counter?.view?.Content(
                    params = CounterParameters(
                        initialCount = productQuantity,
                        onCounterChanged = { changeCount(it) },
                        lightMode = true,
                        isDisable = isLocked,
                        isLoading = isLocked
                    )
                )
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = { ignoreProduct() }) {
                        Text(
                            text = Localisation.Ingredient.ignoreProduct.localised,
                            style = TextStyle(fontSize = 14.sp, lineHeight = 16.sp, fontWeight = FontWeight(700), color = grey)
                        )
                    }
                    Surface(
                        Modifier
                            .size(40.dp)
                            .clickable { addProduct() }, shape =CircleShape, color = primary
                    ) {
                        if (isLocked) {
                            Row(
                                modifier = Modifier
                                    .size(40.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = white, modifier = Modifier.size(20.dp))
                            }
                        } else {
                            Image(
                                painter = painterResource(id = cart),
                                contentDescription = "buy",
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}