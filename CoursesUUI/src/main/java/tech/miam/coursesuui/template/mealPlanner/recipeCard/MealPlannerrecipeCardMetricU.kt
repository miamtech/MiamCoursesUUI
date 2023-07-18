package tech.miam.coursesuui.template.mealPlanner.recipeCard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.miam.coursesuui.theme.Typography
import com.miam.kmm_miam_sdk.android.theme.Colors

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun RecipeCardMetric(text: String, image: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .height(35.dp)
            .padding(horizontal = 8.dp)
            .composed { modifier }
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Colors.grey),
            modifier = Modifier.size(17.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = Colors.grey,
            style = Typography.body,
            textAlign = TextAlign.Center,
        )
    }
}