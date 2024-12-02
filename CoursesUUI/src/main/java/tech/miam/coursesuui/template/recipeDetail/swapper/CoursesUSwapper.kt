package tech.miam.coursesuui.template.recipeDetail.swapper

import ai.mealz.sdk.components.baseComponent.swapper.Swapper
import ai.mealz.sdk.components.baseComponent.swapper.SwapperImp
import ai.mealz.sdk.components.baseComponent.swapper.SwapperParameters
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class CoursesUSwapper: Swapper {

    @Composable
    override fun Content(params: SwapperParameters) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            SwapperImp().Content(params = params)
        }
    }
}
