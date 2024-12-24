package tech.miam.coursesuui.template.catalog.categoryPage

import ai.mealz.sdk.components.catalog.success.categoriesPage.discountButton.DiscountButton
import ai.mealz.sdk.components.catalog.success.categoriesPage.discountButton.DiscountButtonParameters
import ai.mealz.sdk.components.states.Empty
import androidx.compose.runtime.Composable

class CoursesUDiscountButton : DiscountButton {
    @Composable
    override fun Content(param: DiscountButtonParameters) {
        Empty()
    }
}