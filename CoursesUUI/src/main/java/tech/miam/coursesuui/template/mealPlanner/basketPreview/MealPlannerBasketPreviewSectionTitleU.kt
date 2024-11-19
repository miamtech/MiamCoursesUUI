package tech.miam.coursesuui.template.mealPlanner.basketPreview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ai.mealz.sdk.components.mealPlanner.basketPreview.success.notInBasket.header.NotInBasketProductHeader
import ai.mealz.sdk.components.mealPlanner.basketPreview.success.notInBasket.header.NotInBasketProductHeaderParameters
import tech.miam.coursesuui.R

class MealPlannerBasketPreviewSectionTitleU: NotInBasketProductHeader {
    @Composable
    override fun Content(params: NotInBasketProductHeaderParameters) {
        var expandedState by remember { mutableStateOf(params.isExpanded) }
        val rotationState by animateFloatAsState(
            targetValue = if (expandedState) 90f else 0f
        )

        fun toggle() {
            expandedState = !expandedState
        }

        Box(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp)
                .padding(vertical = 10.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    params.toggle()
                    toggle()
                }.background(Color(0xffDDDDDD))
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(vertical = 5.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f),
                        text = params.title,
                        fontWeight = if (expandedState) FontWeight.Bold else FontWeight.Normal
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_chevron_u),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier
                            .size(24.dp)
                            .weight(1f)
                            .rotate(90f)
                            .rotate(rotationState)
                    )
                }
            }
        }
    }
}
