package tech.miam.coursesuui.template.recipeDetail.success.header

import ai.mealz.sdk.components.baseComponent.likeButton.LikeButton
import ai.mealz.sdk.components.recipeDetail.success.header.RecipeDetailHeader
import ai.mealz.sdk.components.recipeDetail.success.header.RecipeDetailHeaderParameters
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography.subtitleBold
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class CoursesURecipeDetailHeader : RecipeDetailHeader {
    @Composable
    override fun Content(params: RecipeDetailHeaderParameters) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = if (params.scrollPosition > 900) Colors.white else Color.Transparent)
                .fillMaxWidth()
        ) {
            Box(Modifier.padding(16.dp)) {
                Surface(
                    Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                    shape = CircleShape,
                    color = Colors.white,
                    elevation = 1.dp
                ) {}
                Image(
                    painter = painterResource(ai.mealz.sdk.ressource.Image.toggleCaret),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp)
                        .padding(end = 4.dp)
                        .rotate(180f)
                        .clickable { params.closeDialogue() }
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
                        style = subtitleBold
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
