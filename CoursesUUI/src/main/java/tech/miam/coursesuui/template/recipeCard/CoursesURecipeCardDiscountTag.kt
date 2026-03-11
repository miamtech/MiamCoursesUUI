package tech.miam.coursesuui.template.recipeCard

import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.ressource.Image.discount
import ai.mealz.sdk.theme.Colors.white
import ai.mealz.sdk.theme.Dimension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import tech.miam.coursesuui.theme.Typography

@Composable
fun CoursesURecipeCardDiscountTag(
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(Dimension.sRoundedCorner))
            .then(modifier),
        shape = RoundedCornerShape(Dimension.sRoundedCorner),
        color = tech.miam.coursesuui.theme.Colors.promo
    ) {
        Row(
            Modifier.padding(
                horizontal = Dimension.mPadding,
                vertical = Dimension.sPadding
            ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(discount),
                contentDescription = "discount icon",
                modifier = Modifier.size(Dimension.lIconHeight)
            )

            Spacer(modifier = Modifier.width(Dimension.mPadding))
            Text(
                text = Localisation.recipe.discount.localised,
                style = Typography.bodyMedium,
                color = white
            )
        }
    }
}