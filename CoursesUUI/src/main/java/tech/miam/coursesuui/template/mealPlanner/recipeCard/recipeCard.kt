package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.runtime.Composable
import com.miam.sdk.templateInterfaces.mealPlanner.recipe.MealPlannerRecipeCard
import com.miam.sdk.templateParameters.mealPlanner.recipe.MealPlannerRecipeCardParameters

class MealPlannerRecipeCardU: MealPlannerRecipeCard {
    @Composable
    override fun Content(mealPlannerRecipeCardParameters: MealPlannerRecipeCardParameters) {
        if (mealPlannerRecipeCardParameters.isInSearchPage)
            RecipeCardSearch(params = mealPlannerRecipeCardParameters)
        else
            RecipeCardMealsList(params = mealPlannerRecipeCardParameters)
    }
}