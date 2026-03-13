package tech.miam.coursesuui.component

import ai.mealz.core.localisation.Localisation
import ai.mealz.core.model.DiscountType
import ai.mealz.sdk.theme.Dimension
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import tech.miam.coursesuui.theme.Typography

@Composable
fun CoursesUProductDiscountTagMessage(
    discountAmount: Double,
    discountType: DiscountType,
    modifier: Modifier = Modifier,
    noDiscountView: @Composable (() -> Unit) = @Composable { Box{} }
) {
    when (discountType) {
        DiscountType.STRIKETHROUGH_PRICE, DiscountType.STRIKETHROUGH_PRICE_PERCENTAGE -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimension.sPadding)
                    .border(
                        width = Dimension.borderWidth,
                        color = tech.miam.coursesuui.theme.Colors.promo,
                        shape = RoundedCornerShape(Dimension.sRoundedCorner)
                    )
                    .padding(Dimension.sPadding)
            ) {
                Text(
                    text = "${discountType.formatDiscountAmount(discountAmount)}  ${Localisation.ingredient.immediateDiscount.localised}",
                    color = tech.miam.coursesuui.theme.Colors.promo,
                    textAlign = TextAlign.Center,
                    style = Typography.bodyMedium,
                    modifier = Modifier.padding(Dimension.sPadding).fillMaxWidth()
                )
            }
        }
        else -> noDiscountView()
    }
}