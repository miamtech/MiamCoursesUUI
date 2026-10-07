package tech.miam.coursesuui.component

import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.miam.coursesuui.R

@Composable
fun CoursesUMealzHeader(
    title: String,
    close: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = colorResource(id = R.color.miam_courses_u_dark_background))
            .fillMaxWidth()
    ) {
        Box(Modifier.padding(16.dp)) {
            Surface(
                Modifier
                    .size(36.dp)
                    .align(Alignment.Center),
                shape = CircleShape,
                color = colorResource(id = R.color.miam_courses_u_dark_background),
                elevation = 1.dp,
                border = BorderStroke(width = 1.dp, Colors.white)
            ) {}
            Image(
                painter = painterResource(id = R.drawable.mealz_arrow),
                contentDescription = "go back",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
                    .clickable { close() },
                colorFilter = ColorFilter.tint(Colors.white)
            )
        }
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title, Modifier.weight(1f),
                textAlign = TextAlign.Left,
                style = Typography.subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                color = Colors.white
            )
        }
    }
}