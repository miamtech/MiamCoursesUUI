package tech.miam.coursesuui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import ai.mealz.sdk.theme.Dimension
import ai.mealz.sdk.theme.Typography
import tech.miam.coursesuui.R


@Composable
fun RecapPriceForRecipes(
    leadingText: String = "",
    priceAmount: String,
    trailingText: String = "le repas",
    leadingPadding: Dp = Dimension.mPadding,
    trailingPadding: Dp = Dimension.mPadding,
    textFontStyle: TextStyle = Typography.body, // replace with your own TextStyle
    yellowSubtextFontStyle: TextStyle = Typography.bodyBold, // replace with your own TextStyle
    yellowSubtextWidth: Dp = 45.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = Dimension.sPadding)
            .padding(start = leadingPadding)
            .padding(end = trailingPadding),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Dimension.sRoundedCorner))
                .background(Color(R.color.miam_courses_u_success))
        ) {
        Row(
            modifier = Modifier
                .padding(vertical = Dimension.mPadding)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(Dimension.mPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = leadingText, color = Color.Black, style = textFontStyle)

            YellowSubtext(
                text = priceAmount,
                fontStyle = yellowSubtextFontStyle,
                imageWidth = yellowSubtextWidth
            )
            Text(text = trailingText, color = Color.Black, style = textFontStyle)
        }
    }
}
}


@Preview
@Composable
fun RecapPriceForRecipesPreview() {
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        RecapPriceForRecipes(priceAmount = "14,96€")
        RecapPriceForRecipes(leadingText = "Soit", priceAmount = "14,96€")
        RecapPriceForRecipes(priceAmount = "15,33", textFontStyle = Typography.subtitle) // Replace with your TextStyle
        RecapPriceForRecipes(priceAmount = "12,56", yellowSubtextFontStyle = Typography.subtitleBold, yellowSubtextWidth = 60.dp)
    }
}
