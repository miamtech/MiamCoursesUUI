package tech.miam.coursesuui.template.recipeDetail.success.header

import ai.mealz.sdk.components.baseComponent.likeButton.LikeButton
import ai.mealz.sdk.components.recipeDetail.success.header.RecipeDetailHeader
import ai.mealz.sdk.components.recipeDetail.success.header.RecipeDetailHeaderParameters
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography.subtitleBold
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.miam.coursesuui.R

class CoursesURecipeDetailHeader : RecipeDetailHeader {

    @Composable
    override fun Content(params: RecipeDetailHeaderParameters) {
        var scrolledPastPoint: Boolean = params.scrollPosition > 900

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = if (scrolledPastPoint) colorResource(id = R.color.miam_courses_u_dark_background) else Color.Transparent)
                .fillMaxWidth()
        ) {
            Box(Modifier.padding(16.dp)) {
                Surface(
                    Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                    shape = CircleShape,
                    color = if (scrolledPastPoint) colorResource(id = R.color.miam_courses_u_dark_background) else Colors.white,
                    elevation = 1.dp,
                    border = BorderStroke(width = 1.dp, Colors.white)
                ) {}
                Image(
                    painter = painterResource(id = R.drawable.mealz_arrow),
                    contentDescription = "go back",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp)
                        .clickable { params.closeDialogue() },
                    colorFilter = ColorFilter.tint(if (scrolledPastPoint) Colors.white else Colors.primary)
                )
            }
            if (params.scrollPosition > 900) {
                AnimatedVisibility(
                    visible = true,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = params.title, Modifier.weight(1f),
                        textAlign = TextAlign.Left,
                        style = subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                        color = Colors.white
                    )
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            if (params.isLikeEnabled) {
                Box(Modifier.padding(16.dp)) {
                    Surface(shape = CircleShape,
                        color = Colors.white ,
                        modifier = Modifier.size(36.dp)
                    ) {
                    }
                    LikeButton(RectangleShape, recipeId = params.recipeId).Content()
                }
            }
        }
    }
}
