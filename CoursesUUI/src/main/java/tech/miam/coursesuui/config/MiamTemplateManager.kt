package tech.miam.coursesuui.config

import ai.mealz.core.localisation.Localisation
import ai.mealz.core.model.Ingredient
import ai.mealz.core.model.RecipeDifficulty
import ai.mealz.core.viewModels.quantityFormatter.QuantityFormatter
import ai.mealz.sdk.components.MiamTheme
import ai.mealz.sdk.components.MiamTheme.catalog
import ai.mealz.sdk.components.MiamTheme.defaultViews
import ai.mealz.sdk.components.MiamTheme.favoritePage
import ai.mealz.sdk.components.MiamTheme.itemSelector
import ai.mealz.sdk.components.MiamTheme.likeButton
import ai.mealz.sdk.components.MiamTheme.myMeal
import ai.mealz.sdk.components.MiamTheme.myMealButton
import ai.mealz.sdk.components.MiamTheme.mySpace
import ai.mealz.sdk.components.MiamTheme.orderHistory
import ai.mealz.sdk.components.MiamTheme.price
import ai.mealz.sdk.components.MiamTheme.recipeCard
import ai.mealz.sdk.components.MiamTheme.recipeDetail
import ai.mealz.sdk.components.baseComponent.recipesPage.IngredientImage
import ai.mealz.sdk.components.baseComponent.tag.Tag
import ai.mealz.sdk.components.itemSelector.header.ItemSelectorHeader
import ai.mealz.sdk.components.itemSelector.header.ItemSelectorHeaderParameters
import ai.mealz.sdk.components.recipeDetail.success.ingredients.Ingredients
import ai.mealz.sdk.components.recipeDetail.success.ingredients.IngredientsParameters
import ai.mealz.sdk.components.recipeDetail.success.productListHeader.RecipeDetailProductListHeader
import ai.mealz.sdk.components.recipeDetail.success.productListHeader.RecipeDetailProductListHeaderParameters
import ai.mealz.sdk.components.recipeDetail.success.sponsorBanner.RecipeDetailSponsorBanner
import ai.mealz.sdk.components.recipeDetail.success.sponsorBanner.RecipeDetailSponsorBannerParameters
import ai.mealz.sdk.components.recipeDetail.success.tag.RecipeDetailSuccessTag
import ai.mealz.sdk.components.recipeDetail.success.tag.RecipeDetailSuccessTagParameters
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Dimension
import ai.mealz.sdk.theme.Dimension.xlButtonHeight
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import tech.miam.coursesuui.R
import tech.miam.coursesuui.component.CoursesUEmptyPage
import tech.miam.coursesuui.component.CoursesUGuestsCounter
import tech.miam.coursesuui.template.itemSelector.CoursesUItemSelectorNoResults
import tech.miam.coursesuui.template.itemSelector.CoursesUItemSelectorSearch
import tech.miam.coursesuui.template.itemSelector.CoursesUSelectItemSuccess
import tech.miam.coursesuui.template.itemSelector.CoursesUSelectedItem
import tech.miam.coursesuui.template.likeButton.CoursesULikeButtonLoading
import tech.miam.coursesuui.template.likeButton.CoursesULikeButtonSuccess
import tech.miam.coursesuui.template.myMeal.CoursesUMyMealButton
import tech.miam.coursesuui.template.myMeal.CoursesUMyMealHeader
import tech.miam.coursesuui.template.myMeal.CoursesUMyMealRecipe
import tech.miam.coursesuui.template.price.footer.CoursesURecipeDetailCookOnlyFooter
import tech.miam.coursesuui.template.recipeCard.CoursesUCatalogCategoryRecipeCard
import tech.miam.coursesuui.template.recipeCard.StandaloneCoursesURecipeCard
import tech.miam.coursesuui.template.recipeDetail.footer.CoursesURecipeDetailFooter
import tech.miam.coursesuui.template.recipeDetail.info.CoursesURecipeDetailInfo
import tech.miam.coursesuui.template.recipeDetail.loading.CoursesUProductLoading
import tech.miam.coursesuui.template.recipeDetail.removeFromBasket.CoursesUProductRemovedFromBasket
import tech.miam.coursesuui.template.recipeDetail.success.CoursesUStep
import tech.miam.coursesuui.template.recipeDetail.success.header.CoursesURecipeDetailHeader
import tech.miam.coursesuui.template.recipeDetail.success.product.CoursesUProduct
import tech.miam.coursesuui.template.recipeDetail.success.product.CoursesUProductCounter
import tech.miam.coursesuui.template.recipeDetail.swapper.CoursesUSwapper
import tech.miam.coursesuui.template.recipeDetail.tags.CoursesUTags
import java.util.Locale
import kotlin.math.ceil
import kotlin.time.Duration


class MiamTemplateManager {
    init {
        overrideMealzColors()
        overrideIcon()
        MiamTheme.Template {
            ////////   MEAL PLANNER  END TEMPLATING /////////////////////
            /////// DEFAULT VIEW TEMPLATING //////////////////
            defaultViews {
                empty {
                    view = CoursesUEmptyPage()
                }
            }
            /////// END DEFAULT VIEW TEMPLATING //////////////////

            ///////// RECIPE CARD TEMPLATING //////////////////

            recipeCard {
                success {
                    shelf {
                        view = StandaloneCoursesURecipeCard()
                    }
                    catalog {
                        view = CoursesUCatalogCategoryRecipeCard()
                    }
                }
                loading {
                    //view = CoursesURecipeCardLoading()
                }
            }
            ///////// END RECIPE CARD TEMPLATING //////////////

            //////// RECIPE DETAIL /////////////
            recipeDetail {
                success {
                    productListHeader {
                        view = CoursesURecipeDetailProductListHeaderImp()
                    }
                    ingredients { view = CoursesUIngredientsImp() }
                    oftenDeleted {
                        product {
                            view = CoursesUProductRemovedFromBasket()
                        }
                        info {
                            view = CoursesURecipeDetailInfo()
                        }
                    }
                    info {
                        counter {
                            view = CoursesUGuestsCounter()
                        }
                    }
                    segmentedButtonRow { view = CoursesUSwapper() }
                    products {
                        counter {
                            view = CoursesUProductCounter()
                        }
                        success {
                            view = CoursesUProduct()
                        }
                        ignore {
                            view = CoursesUProductRemovedFromBasket()
                        }
                        loading {
                            view = CoursesUProductLoading()
                        }
                        steps {
                            view = CoursesUStep()
                        }
                        tag {
                            view = CoursesUTags()
                        }
                        footer {
                            view = CoursesURecipeDetailFooter()
                            height = 100.dp
                        }
                    }
                    header {
                        view = CoursesURecipeDetailHeader()
                    }
                    sponsorBanner { view = CoursesURecipeDetailSponsorBanner() }
                    tag { view = CoursesURecipeDetailSuccessTag() }
                }
            }
            ///////// END RECIPE DETAIL //////////
            //////// ITEM SELECTOR //////////
            itemSelector {
                header { view = CoursesUItemSelectorHeader() }
                search {
                    view = CoursesUItemSelectorSearch()
                }
                selectedItem {
                    view = CoursesUSelectedItem()
                }
                success {
                    view = CoursesUSelectItemSuccess()
                }
                noResults {
                    view = CoursesUItemSelectorNoResults()
                }
            }
            ////// END ITEM SELECTOR //////////
            ////// MY MEAL  //////////
            myMeal {
                header { view = CoursesUMyMealHeader() }
                recipeCard {
                    success { view = CoursesUMyMealRecipe() }
                }
                empty { view = CoursesUEmptyPage("Vous n'avez aucune idées repas") }
            }
            ///// END MY MEAL  //////////
            //// PRICE  //////////
            price {
                footerPrice {
                    success {
                        view = CoursesURecipeDetailCookOnlyFooter()
                    }
                }
            }
            //// END PRICE  //////////

            //// LIKE BUTTON //////////
            likeButton {
                loading {
                    view = CoursesULikeButtonLoading()
                }
                success {
                    view = CoursesULikeButtonSuccess()
                }
            }
            //// END LIKE BUTTON //////////
            //// MY MEAL BUTTON  //////////$
            myMealButton {
                success {
                    view = CoursesUMyMealButton()
                }
            }
            //// END MY MEAL BUTTON  //////////

            catalog {
                displayVariant = DISPLAY_VARIANT
            }

            // region MySpace and sub pages
            mySpace {
                displayVariant = DISPLAY_VARIANT
            }
            favoritePage {
                displayVariant = DISPLAY_VARIANT
            }
            orderHistory {
                displayVariant = DISPLAY_VARIANT
                //loading {  }
            }
            // endregion
        }
    }

    private fun overrideIcon() {
        Image.favorite = R.drawable.ic_favourite_unselected
        Image.favoriteFilled = R.drawable.ic_favourite_selected
        Image.filter = R.drawable.ic_filters
        Image.search = R.drawable.search
        Image.cart = R.drawable.basket
        Image.check = R.drawable.ic_cart_check
    }

    private fun overrideMealzColors() {
        Colors.primary = Color(0, 125, 143)
    }

    companion object {
        private val DISPLAY_VARIANT: Int = 3
    }
}

class CoursesURecipeDetailProductListHeaderImp: RecipeDetailProductListHeader {
    @Composable
    override fun Content(params: RecipeDetailProductListHeaderParameters) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = Localisation.recipe.numberOfIngredients(params.productCount).localised,
                style = ai.mealz.sdk.theme.Typography.subtitleBold.copy(textAlign = TextAlign.Start).copy(fontFamily = FontFamily(
                    Font(R.font.mealz_mullish)
                )
                ),
                color = Colors.black
            )
            if (params.outOfStockProductCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Tag(
                    icon = Image.warning,
                    message = Localisation.recipeDetails.productsUnavailable(params.outOfStockProductCount).localised,
                    backGroundColor = Colors.warningBackground,
                    textColor = Colors.warning
                )
            }
        }
    }
}

class CoursesUIngredientsImp : Ingredients {
    @Composable
    override fun Content(params: IngredientsParameters) {
        Column(
            Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = Localisation.recipe.numberOfIngredients(params.ingredients.size).localised,
                style = ai.mealz.sdk.theme.Typography.subtitleBold.copy(textAlign = TextAlign.Start).copy(fontFamily = FontFamily(
                    Font(R.font.mealz_mullish)
                )),
                color = ai.mealz.sdk.theme.Colors.black
            )

            params.ingredients.groupBy { ceil((params.ingredients.indexOf(it) + 1.0) / 3.0) }
                .map { it.value }.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        row.forEach {
                            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                CoursesUIngredient(it, params.guestsCount, params.defaultRecipeGuest)
                            }
                        }
                        for (i in 3 - row.size downTo 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
        }
    }

    @Composable
    fun CoursesUIngredient(ingredient: Ingredient, guestsCount: Int, defaultRecipeGuest: Int) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IngredientImage(ingredient.attributes?.pictureUrl)
            Text(
                text = ingredient.attributes?.name?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                } ?: "",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight(900),
                    color = ai.mealz.sdk.theme.Colors.boldText,
                    textAlign = TextAlign.Center,
                ).copy(fontFamily = FontFamily(
                    Font(R.font.mealz_mullish)
                ))
            )
            Text(
                text = QuantityFormatter.readableFloatNumber(
                    value = QuantityFormatter.realQuantities(
                        // Will never append ingredient must have a quantity
                        ingredient.attributes?.quantity ?: "1",
                        guestsCount,
                        // Will never append recipe must have a numberOfGuests
                        defaultRecipeGuest
                    ),
                    unit = ingredient.attributes?.unit
                ), textAlign = TextAlign.Center
            )
        }
    }
}

class CoursesURecipeDetailSponsorBanner: RecipeDetailSponsorBanner {
    @Composable
    override fun Content(params: RecipeDetailSponsorBannerParameters) {
        Column {
            Row(
                Modifier
                    .background(Color.Transparent)
                    .padding(16.dp)
                    .fillMaxWidth(),
                Arrangement.SpaceBetween
            ) {
                Column(Modifier.fillMaxWidth(0.7f)) {
                    Text(text = Localisation.sponsorBanner.sponsorBannerSpeach.localised, style = ai.mealz.sdk.theme.Typography.body.copy(fontFamily = FontFamily(
                        Font(R.font.mealz_mullish)
                    )))
                    if (params.hasSponsorDetailsInformation) {
                        Spacer(Modifier.size(4.dp))
                        Text(
                            text = Localisation.sponsorBanner.sponsorBannerMoreInfo.localised,
                            style = ai.mealz.sdk.theme.Typography.link.copy(
                                fontFamily = FontFamily(
                                    Font(R.font.mealz_mullish)
                                )
                            ),
                            color = ai.mealz.sdk.theme.Colors.primary,
                            modifier = Modifier.clickable { params.openSponsorDetail(params.sponsor) }
                        )
                    }
                }
                params.sponsor.attributes?.let {
                    AsyncImage(
                        modifier = Modifier
                            .heightIn(0.dp, 96.dp),
                        model = it.logoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                    )
                }
            }
            Divider()
        }
    }
}

class CoursesURecipeDetailSuccessTag: RecipeDetailSuccessTag {
    @Composable
    override fun Content(params: RecipeDetailSuccessTagParameters) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = params.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Left,
                    style = ai.mealz.sdk.theme.Typography.subtitleBold.copy(fontFamily = FontFamily(
                        Font(R.font.mealz_mullish)
                    ))
                )
            }
            RecipeDifficultyAndTiming(
                params.difficulty,
                params.preparationTime,
                params.cookingTime,
            )
        }
    }

    @Composable
    fun RecipeDifficultyAndTiming(
        difficulty: RecipeDifficulty,
        preparationTime: Duration?,
        cookingTime: Duration?,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Time(ai.mealz.sdk.ressource.Image.miamPreparation, preparationTime)
            Time(ai.mealz.sdk.ressource.Image.miamCook, cookingTime)
            when (difficulty) {
                RecipeDifficulty.Easy -> RecipeDifficulty(ai.mealz.sdk.ressource.Image.miamDifficulty, Localisation.recipe.lowDifficulty.localised)
                RecipeDifficulty.Medium -> RecipeDifficulty(ai.mealz.sdk.ressource.Image.miamDifficulty, Localisation.recipe.mediumDifficulty.localised)
                RecipeDifficulty.Hard -> RecipeDifficulty(ai.mealz.sdk.ressource.Image.miamDifficulty, Localisation.recipe.highDifficulty.localised)
                else -> {
                    RecipeDifficulty(ai.mealz.sdk.ressource.Image.difficulty, Localisation.recipe.lowDifficulty.localised)
                }
            }
        }
    }

    @Composable
    fun RecipeDifficulty(imageRef: Int, difficultyLabel: String) {
        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(100.dp), color = ai.mealz.sdk.theme.Colors.backgroundGrey)
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(imageRef),
                contentDescription = "Recipe Difficulty",
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = difficultyLabel,
                style = TextStyle(ai.mealz.sdk.theme.Colors.boldText, fontSize = 16.sp)
            )
        }
    }

    @Composable
    fun Time(image: Int, time: Duration?) {
        if (time?.inWholeSeconds != 0.toLong()) {
            Row(
                modifier = Modifier
                    .background(shape = RoundedCornerShape(100.dp), color = ai.mealz.sdk.theme.Colors.backgroundGrey)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(painter = painterResource(image), contentDescription = "$image", modifier = Modifier.height(20.dp))
                Text(text = "$time", style = TextStyle(ai.mealz.sdk.theme.Colors.boldText, fontSize = 16.sp))
            }
        }
    }
}

class CoursesUItemSelectorHeader: ItemSelectorHeader {
    @Composable
    override fun Content(params: ItemSelectorHeaderParameters) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                modifier = Modifier
                    .size(xlButtonHeight)
                    .align(Alignment.CenterStart),
                onClick = params.previous
            ) {
                Image(
                    colorFilter = ColorFilter.tint(ai.mealz.sdk.theme.Colors.primary),
                    painter = painterResource(ai.mealz.sdk.ressource.Image.toggleCaret),
                    contentDescription = "Previous",
                    modifier = Modifier.rotate(180f)
                )
            }
            Text(
                text = params.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold).copy(fontFamily = FontFamily(
                    Font(R.font.mealz_mullish)
                ))
            )
        }
    }
}

@Composable
fun CoursesUSearchBar(searchPlaceholder: String, search: (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(Dimension.borderWidth, Colors.grey),
                shape = RoundedCornerShape(Dimension.mRoundedCorner)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            leadingIcon = { Icon(Icons.Filled.Search, "Search icon") },
            trailingIcon = {
                if (textFieldValue.text.isNotEmpty()) {
                    Icon(
                        Icons.Filled.Close, "Close icon",
                        Modifier.clickable {
                            textFieldValue = textFieldValue.copy(text = "")
                            search("")
                        }
                    )
                }
            },
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                search(it.text)
            },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    searchPlaceholder,
                    style = TextStyle(fontSize = 16.sp).copy(fontFamily = FontFamily(
                        Font(R.font.mealz_mullish)
                    )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            textStyle = TextStyle().copy(fontFamily = FontFamily(
                Font(R.font.mealz_mullish)
            )
            ),
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
    }
}