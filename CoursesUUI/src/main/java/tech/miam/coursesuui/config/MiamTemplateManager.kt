package tech.miam.coursesuui.config

import com.miam.sdk.templatesConfigs.MiamTheme
import com.miam.sdk.templatesConfigs.MiamTheme.budget
import com.miam.sdk.templatesConfigs.MiamTheme.mealPlanner
import tech.miam.coursesuui.component.CoursesUMealPlannerFooter
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


class MiamTemplateManager {
    init {
        MiamTheme.Template {
            budget {
                plannerFooterConfig {
                    view = CoursesUMealPlannerFooter()
                }
                plannerToolbarConfig {
                    view = CoursesUBudgetPlannerToolbar()
                }
                searchConfig {
                    view = MealPlannerReplaceRecipeSearchU()
                }
            }
            mealPlanner {
                callToActionConfig {
                    view = MealPlannerCallToActionU()
                }
                basketPreviewProductConfig {
                    view = CoursesUBasketPreviewProductImp()
                }
                basketPreviewFooterConfig {
                    view = CoursesUMealPlannerFooter()
                }
                basketPreviewSectionTitleConfig {
                    view = MealPlannerBasketPreviewSectionTitleU()
                }
                basketPreviewRecipeOverviewConfig {
                    view = RecipeCardOverview()
                }
                formConfig {
                    view = CoursesUBudgetForm()
                }
                recapConfig {
                    view = MealPlannerRecapU()
                }
                recipeCardConfig {
                    view = MealPlannerRecipeCardU()
                    recipePlaceholderConfig {
                        view = MealPlannerRecipePlaceholderU()
                    }
                    recipeLoadingConfig {
                        view = RecipeLoadingViewU()
                    }
                }
                searchEmpty {
                    view = MealPlannerSearchEmptyU()
                }
            }
        }
    }
}