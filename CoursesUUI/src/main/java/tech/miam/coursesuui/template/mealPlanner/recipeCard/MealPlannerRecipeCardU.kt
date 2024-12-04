package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.runtime.Composable
import ai.mealz.sdk.components.mealPlanner.recipe.success.MealPlannerRecipeCardSuccessParameters
import ai.mealz.sdk.components.mealPlanner.recipe.success.MealPlannerRecipeSuccessCard
import tech.miam.coursesuui.template.mealPlanner.meals.CoursesURecipeCardMealsList


class MealPlannerRecipeCardU: MealPlannerRecipeSuccessCard {
    @Composable
    override fun Content(mealPlannerRecipeCardParameters: MealPlannerRecipeCardSuccessParameters) {
        if (mealPlannerRecipeCardParameters.isInSearchPage)
            CoursesURecipeCardSearch(params = mealPlannerRecipeCardParameters)
        else
            CoursesURecipeCardMealsList(params = mealPlannerRecipeCardParameters)
    }
}