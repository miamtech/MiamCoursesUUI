package tech.miam.coursesuui.template.recipeDetail.success.product

import ai.mealz.sdk.components.recipeDetail.success.product.success.ProductSuccess
import ai.mealz.sdk.components.recipeDetail.success.product.success.ProductSuccessParameters
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
import ai.mealz.core.localisation.Localisation
import ai.mealz.core.viewModels.quantityFormatter.QuantityFormatter
import ai.mealz.sdk.ressource.Image.cart
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Colors.black
import ai.mealz.sdk.theme.Colors.boldText
import ai.mealz.sdk.theme.Colors.grey
import ai.mealz.sdk.theme.Colors.lightgrey
import ai.mealz.sdk.theme.Colors.primary
import ai.mealz.sdk.theme.Colors.white
import ai.mealz.sdk.components.baseComponent.counter.CounterParameters
import ai.mealz.sdk.di.TemplateDI
import ai.mealz.sdk.theme.Dimension
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

class CoursesUProduct: ProductSuccess {
    @Composable
    override fun Content(params: ProductSuccessParameters) {

        val guestsCount = params.guestsCount.collectAsState()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimension.lPadding),
            shape = RoundedCornerShape(Dimension.mRoundedCorner),
            border = BorderStroke(1.dp, if (params.isInBasket) primary else lightgrey)
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
                Surface(modifier = Modifier.height(32.dp).padding(vertical = 12.dp)) {}
                ProductInformation(
                    params.productName,
                    params.productBrand,
                    params.productCapacityVolume,
                    params.productImage,
                    params.pricePerUnitOfMeasure,
                    params.isSponsored
                )
                ActionRow(
                    params.formattedUnitPrice,
                    params.productQuantity,
                    params.isInBasket,
                    params.isLocked,
                    params.addProduct,
                    params.updateProductQuantity
                )
                ReplaceOrIgnoreRow(
                    params.isLocked,
                    params.ignoreProduct,
                    params.replaceProduct
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
                            text = Localisation.recipeDetailsProduct.productsSharedRecipe(params.numberOfRecipeConcernsByProduct).localised,
                            style = TextStyle(fontSize = 12.sp, color = grey)
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
        combinedCapacity: String,
        productImage: String,
        pricePerUnitOfMeasurement: String,
        isSponsor: Boolean,
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
                            text = combinedCapacity,
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight(500), color = boldText)
                        )
                    }
                    if (isSponsor) {
                        Text(
                            text = Localisation.recipeDetailsProduct.sponsored.localised,
                            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight(500), color = boldText),
                            modifier = Modifier
                                .background(Color.Transparent, shape = RoundedCornerShape(100.dp))
                                .border(BorderStroke(1.dp, lightgrey), shape = RoundedCornerShape(100.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                    Text(
                        text = pricePerUnitOfMeasurement,
                        modifier = Modifier.align(Alignment.CenterVertically)
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
        changeCount: (Int) -> Unit
    ) {
        Row(
            modifier = Modifier
                .padding(top = 12.dp)
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

    @Composable
    fun ReplaceOrIgnoreRow(
        disable: Boolean,
        ignoreProduct: () -> Unit,
        replaceProduct: () -> Unit

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimension.mPadding)
        ) {
            TextButton(onClick = { ignoreProduct() }, enabled = !disable) {
                Text(
                    text = Localisation.recipeDetailsProduct.ignoreProduct.localised,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(700),
                        color = grey
                    )
                )
            }
            Spacer(modifier = Modifier.padding(Dimension.mPadding))
            TextButton(onClick = { replaceProduct() }, enabled = !disable) {
                Text(
                    text = Localisation.recipeDetailsProduct.replaceItem.localised,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(700),
                        color = primary
                    )
                )
            }
        }
    }
}