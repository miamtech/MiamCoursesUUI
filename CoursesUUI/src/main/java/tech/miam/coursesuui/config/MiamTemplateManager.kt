package tech.miam.coursesuui.config

import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.sdk.components.MiamTheme
import com.miam.sdk.components.MiamTheme.catalog
import com.miam.sdk.components.MiamTheme.defaultViews
import com.miam.sdk.components.MiamTheme.itemSelector
import com.miam.sdk.components.MiamTheme.likeButton
import com.miam.sdk.components.MiamTheme.mealPlanner
import com.miam.sdk.components.MiamTheme.myMeal
import com.miam.sdk.components.MiamTheme.myMealButton
import com.miam.sdk.components.MiamTheme.price
import com.miam.sdk.components.MiamTheme.recipeCard
import com.miam.sdk.components.MiamTheme.recipeDetail
import com.miam.sdk.components.MiamTheme.recipesPage
import tech.miam.coursesuui.R
import tech.miam.coursesuui.component.CoursesUMealPlannerFooter
import tech.miam.coursesuui.component.EmptyPage
import tech.miam.coursesuui.template.catalog.CoursesUCatalogToolbar
import tech.miam.coursesuui.template.catalog.categoryPage.CoursesUCatalogCategory
import tech.miam.coursesuui.template.itemSelector.CoursesUItemSelectorEmpty
import tech.miam.coursesuui.template.itemSelector.CoursesUItemSelectorSearch
import tech.miam.coursesuui.template.itemSelector.CoursesUSelectItemSuccess
import tech.miam.coursesuui.template.itemSelector.CoursesUSelectedItem
import tech.miam.coursesuui.template.likeButton.CoursesULikeButtonLoading
import tech.miam.coursesuui.template.likeButton.CoursesULikeButtonSuccess
import tech.miam.coursesuui.template.mealPlanner.basketPreview.CoursesUBasketPreviewProductImp
import tech.miam.coursesuui.template.mealPlanner.basketPreview.MealPlannerBasketPreviewSectionTitleU
import tech.miam.coursesuui.template.mealPlanner.basketPreview.RecipeCardOverview
import tech.miam.coursesuui.template.mealPlanner.callToAction.MealPlannerCallToActionU
import tech.miam.coursesuui.template.mealPlanner.form.CoursesUBudgetForm
import tech.miam.coursesuui.template.mealPlanner.planner.CoursesUBudgetPlannerToolbar
import tech.miam.coursesuui.template.mealPlanner.recap.MealPlannerRecapU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.MealPlannerRecipeCardU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.MealPlannerRecipePlaceholderU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.RecipeLoadingViewU
import tech.miam.coursesuui.template.mealPlanner.replaceRecipePage.MealPlannerReplaceRecipeSearchU
import tech.miam.coursesuui.template.mealPlanner.replaceRecipePage.MealPlannerSearchEmptyU
import tech.miam.coursesuui.template.myMeal.CoursesUMyMealButton
import tech.miam.coursesuui.template.myMeal.CoursesUMyMealRecipe
import tech.miam.coursesuui.template.price.footer.CoursesURecipeDetailCookOnlyFooter
import tech.miam.coursesuui.template.recipeCard.CoursesURecipeCard
import tech.miam.coursesuui.template.recipeCard.CoursesURecipeCardLoading
import tech.miam.coursesuui.template.recipeDetail.footer.CoursesURecipeDetailFooter
import tech.miam.coursesuui.template.recipeDetail.info.CoursesURecipeDetailInfo
import tech.miam.coursesuui.template.recipeDetail.loading.CoursesUProductLoading
import tech.miam.coursesuui.template.recipeDetail.removeFromBasket.CoursesUProductRemovedFromBasket
import tech.miam.coursesuui.template.recipeDetail.success.product.CoursesUProduct
import tech.miam.coursesuui.template.recipeDetail.success.CoursesUStep
import tech.miam.coursesuui.template.recipeDetail.success.product.CoursesUProductCounter
import tech.miam.coursesuui.template.recipeDetail.tags.CoursesUTags


class MiamTemplateManager {
    init {
        overrideIcon()
        MiamTheme.Template {
            //////// MEAL PLANNER TEMPLATING /////////////////
            mealPlanner {
                plannerFooterConfig {
                    view = CoursesUMealPlannerFooter()
                }
                plannerToolbarConfig {
                    view = CoursesUBudgetPlannerToolbar()
                }
                searchConfig {
                    view = MealPlannerReplaceRecipeSearchU()
                }
                recipeCardConfig {
                        view = MealPlannerRecipeCardU()
                }
                callToActionConfig {
                    success {
                        view = MealPlannerCallToActionU()
                    }
                }
                basketPreview {
                    success {
                        recipe{
                            view = RecipeCardOverview()
                        }
                        foundProducts {
                            product {
                                view = CoursesUBasketPreviewProductImp()
                            }
                        }
                        notInBasketProducts {
                           header {
                               view = MealPlannerBasketPreviewSectionTitleU()
                           }
                        }
                    }
                    footer {
                        view = CoursesUMealPlannerFooter()
                    }
                }

                formConfig {
                    success {
                        view = CoursesUBudgetForm()
                    }
                }
                recapConfig {
                    view = MealPlannerRecapU()
                }

                // TODO should be in recipe config
                recipePlaceholderConfig {
                    view = MealPlannerRecipePlaceholderU()
                }
                // TODO should be in recipe config
                recipeLoadingConfig {
                    view = RecipeLoadingViewU()
                }
                searchEmpty {
                    view = MealPlannerSearchEmptyU()
                }
            }
            ////////   MEAL PLANNER  END TEMPLATING /////////////////////
            /////// DEFAULT VIEW TEMPLATING //////////////////
            defaultViews {
                empty {
                    view = EmptyPage()
                }
            }
            /////// END DEFAULT VIEW TEMPLATING //////////////////

            ///////// RECIPE CARD TEMPLATING //////////////////

            recipeCard {
                success {
                    view = CoursesURecipeCard()
                }
                loading {
                    view = CoursesURecipeCardLoading()
                }
            }
            ///////// END RECIPE CARD TEMPLATING //////////////

            //////// RECIPE DETAIL /////////////
            recipeDetail {
                success {
                    oftenDeleted {
                        product {
                            view = CoursesUProductRemovedFromBasket()
                        }
                        info {
                            view = CoursesURecipeDetailInfo()
                        }
                    }
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
                            view= CoursesUStep()
                        }
                        tag {
                            view = CoursesUTags()
                        }
                        footer {
                            view = CoursesURecipeDetailFooter()
                        }
                    }
                }
            }
            ///////// END RECIPE DETAIL //////////
            //////// ITEM SELECTOR //////////
            itemSelector {
                search {
                    view = CoursesUItemSelectorSearch()
                }
                selectedItem {
                    view = CoursesUSelectedItem()
                }
                success {
                    view = CoursesUSelectItemSuccess()
                }
                empty {
                    view = CoursesUItemSelectorEmpty()
                }
            }
            ////// END ITEM SELECTOR //////////
            ////// MY MEAL  //////////
            myMeal {
                this.
                recipeCard {
                    success {
                        view = CoursesUMyMealRecipe()
                    }
                }
            }
            ///// END MY MEAL  //////////
            //// PRICE  //////////
            price {
                footerPrice{
                    success {
                        view = CoursesURecipeDetailCookOnlyFooter()
                    }
                }
            }
            //// END PRICE  //////////
            //////// CATALOGUE //////////
            catalog {
                success {
                    toolbar {
                        view = CoursesUCatalogToolbar()
                    }
                    categories {
                        category {
                            view = CoursesUCatalogCategory()
                        }
                    }
                }
            }
            //// END CATALOGUE //////////
            //// RECIPE PAGE //////////
            recipesPage {
                success {
                    catalogPageColumns = 2
                    catalogPageHorizontalSpacing = 8
                    catalogPageVerticalSpacing = 8
                }
            }
            //// END RECIPE PAGE //////////
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
}