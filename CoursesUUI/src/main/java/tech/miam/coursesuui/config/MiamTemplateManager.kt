package tech.miam.coursesuui.config

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
import ai.mealz.sdk.components.itemSelector.header.ItemSelectorHeader
import ai.mealz.sdk.components.itemSelector.header.ItemSelectorHeaderParameters
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Dimension.xlButtonHeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tech.miam.coursesuui.R
import tech.miam.coursesuui.component.CoursesUEmptyPage
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
                displayVariant = DISPLAY_VARIANT
            }
            ///////// END RECIPE CARD TEMPLATING //////////////

            //////// RECIPE DETAIL /////////////
            recipeDetail { }
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