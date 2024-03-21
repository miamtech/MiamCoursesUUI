package tech.miam.coursesuui.template.mealPlanner.recipeCard

import ai.mealz.sdk.components.mealPlanner.recipe.success.MealPlannerRecipeCardSuccessParameters
import ai.mealz.sdk.components.mealPlanner.recipe.success.MealPlannerRecipeSuccessCard
import androidx.compose.runtime.Composable



class MealPlannerRecipeCardU: MealPlannerRecipeSuccessCard{
    @Composable
    override fun Content(params: MealPlannerRecipeCardSuccessParameters) {
        if (params.isInSearchPage)
            RecipeCardSearch(params = params)
        else
            RecipeCardMealsList(params = params)
    }
}