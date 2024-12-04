package tech.miam.coursesuui.template.myMeal

import ai.mealz.sdk.components.myMeal.header.MyMealHeader
import ai.mealz.sdk.components.myMeal.header.MyMealHeaderParameters
import androidx.compose.runtime.Composable
import tech.miam.coursesuui.component.CoursesUMealzHeader

class CoursesUMyMealHeader: MyMealHeader {
    @Composable
    override fun Content(params: MyMealHeaderParameters) {
        CoursesUMealzHeader(title = params.text, params.back)
    }
}