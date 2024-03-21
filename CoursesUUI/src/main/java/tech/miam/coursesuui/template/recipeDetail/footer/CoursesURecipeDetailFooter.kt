package tech.miam.coursesuui.template.recipeDetail.footer

import ai.mealz.sdk.components.recipeDetail.success.footer.RecipeDetailSuccessFooter
import ai.mealz.sdk.components.recipeDetail.success.footer.RecipeDetailSuccessFooterParameters
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ai.mealz.core.base.state.ComponentUiState
import ai.mealz.core.localisation.Localisation
import ai.mealz.core.viewModels.dynamicRecipeDetailFooter.IngredientStatusTypes
import ai.mealz.sdk.ressource.Image.cart
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.components.price.formatPrice

class CoursesURecipeDetailFooter: RecipeDetailSuccessFooter {
    @Composable
    override fun Content(params: RecipeDetailSuccessFooterParameters) {

        val price = params.price.collectAsState()
        val isButtonLock = params.isButtonLock.collectAsState()
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .height(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier.weight(1f)) {
                when (params.priceStatus) {
                    ComponentUiState.EMPTY, ComponentUiState.IDLE -> {
                        Box {}
                    }

                    ComponentUiState.SUCCESS, ComponentUiState.LOADING -> Column {

                        if (params.priceStatus == ComponentUiState.LOADING) {
                            Box(Modifier.size(16.dp)) {
                                CircularProgressIndicator(color = Colors.primary)
                            }
                        } else {
                            Text(
                                text = price.value.formatPrice(),
                                style = TextStyle(fontSize = 20.sp, color = Colors.black, fontWeight = FontWeight.Black)
                            )
                        }
                        Text(
                            text = Localisation.Price.perGuest.localised,
                            style = TextStyle(fontSize = 13.sp, color = Colors.grey)
                        )
                    }
                    else -> {}
                }

            }
            if (isButtonLock.value) {
                LoadingButton()
            } else {
                when (params.ingredientsStatus.type) {
                    IngredientStatusTypes.INITIAL_STATE -> AddButton(text = Localisation.RecipeDetails.addAllProducts.localised) { params.onConfirm() }
                    IngredientStatusTypes.NO_MORE_TO_ADD -> ContinueButton(text = Localisation.RecipeDetails.continueShopping.localised) { params.onConfirm() }
                    IngredientStatusTypes.REMAINING_INGREDIENTS_TO_BE_ADDED -> AddButton(text = Localisation.Ingredient.addProduct(params.ingredientsStatus.count).localised) { params.onConfirm() }
                }
            }
        }
    }

    @Composable
    fun LoadingButton() {
        Surface(shape = RoundedCornerShape(50), color = Colors.primary) {
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(Modifier.size(20.dp), Colors.white)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun AddButton(text: String, action: () -> Unit = {}) {
        Surface(
            shape = RoundedCornerShape(50),
            color = Colors.primary,
            onClick = { action() }) {
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(cart), contentDescription = "$cart")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = text, style = TextStyle(fontSize = 14.sp, color = Colors.white, fontWeight = FontWeight(600)))
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ContinueButton(text: String, action: () -> Unit = {}) {
        Surface(
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Colors.primary),
            color = Colors.white,
            onClick = { action() }) {
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = text,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Colors.primary,
                        fontWeight = FontWeight(600)))
            }
        }
    }
}