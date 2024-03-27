package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.runtime.Composable
import com.miam.sdk.components.mealPlanner.recipe.success.MealPlannerRecipeCardSuccessParameters
import com.miam.sdk.components.mealPlanner.recipe.success.MealPlannerRecipeSuccessCard


class MealPlannerRecipeCardU: MealPlannerRecipeSuccessCard {
    @Composable
    override fun Content(mealPlannerRecipeCardParameters: MealPlannerRecipeCardSuccessParameters) {
        if (mealPlannerRecipeCardParameters.isInSearchPage)
            RecipeCardSearch(params = mealPlannerRecipeCardParameters)
        else
            RecipeCardMealsList(params = mealPlannerRecipeCardParameters)
    }
}