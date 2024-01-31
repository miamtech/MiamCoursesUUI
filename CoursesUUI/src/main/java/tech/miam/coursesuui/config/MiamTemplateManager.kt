package tech.miam.coursesuui.config

import com.miam.sdk.components.MiamTheme
import com.miam.sdk.components.MiamTheme.defaultViews
import com.miam.sdk.components.MiamTheme.mealPlanner
import tech.miam.coursesuui.component.CoursesUMealPlannerFooter
import tech.miam.coursesuui.component.EmptyPage
import tech.miam.coursesuui.template.mealPlanner.basketPreview.CoursesUBasketPreviewProductImp
import tech.miam.coursesuui.template.mealPlanner.basketPreview.MealPlannerBasketPreviewSectionTitleU
import tech.miam.coursesuui.template.mealPlanner.basketPreview.RecipeCardOverview
import tech.miam.coursesuui.template.mealPlanner.callToAction.MealPlannerCallToActionU
import tech.miam.coursesuui.template.mealPlanner.form.CoursesUBudgetForm
import tech.miam.coursesuui.template.mealPlanner.planner.CoursesUBudgetPlannerToolbar
import tech.miam.coursesuui.template.mealPlanner.recipeCard.MealPlannerRecipePlaceholderU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.RecipeLoadingViewU
import tech.miam.coursesuui.template.mealPlanner.replaceRecipePage.MealPlannerReplaceRecipeSearchU
import tech.miam.coursesuui.template.mealPlanner.replaceRecipePage.MealPlannerSearchEmptyU


class MiamTemplateManager {
    init {
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
                    //TODO fix in SDK
                    //view = MealPlannerRecapU()
                }

                // TODO should be in recipe config
                recipePlaceholderConfig {
                    view = MealPlannerRecipePlaceholderU()
                }
                // TODO should be in recipe config
                recipeLoadingConfig {
                    view = RecipeLoadingViewU()
                }
                recipeCardConfig {
                    //TODO fix in SDK
                    //view = MealPlannerRecipeCardU()

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
            /////// DEFAULT VIEW TEMPLATING //////////////////
        }

    }
}