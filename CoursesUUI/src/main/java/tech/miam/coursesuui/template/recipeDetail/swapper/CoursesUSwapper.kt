package tech.miam.coursesuui.template.recipeDetail.swapper

import ai.mealz.sdk.components.baseComponent.segmentedButton.SegmentedButtonOption
import ai.mealz.sdk.components.baseComponent.segmentedButton.SegmentedButtonRow
import ai.mealz.sdk.components.baseComponent.segmentedButton.SegmentedButtonRowImp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class CoursesUSwapper: SegmentedButtonRow {

    @Composable
    override fun Content(
        selectedItemIndex: Int,
        options: List<SegmentedButtonOption>,
        onSegmentedButtonSelected: (Int) -> Unit
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            SegmentedButtonRowImp().Content(
                selectedItemIndex = selectedItemIndex,
                options = options,
                onSegmentedButtonSelected = onSegmentedButtonSelected
            )
        }
    }
}
