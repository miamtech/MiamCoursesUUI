package tech.miam.coursesuui.template.recipeDetail.tags

import androidx.compose.foundation.BorderStroke
import com.miam.sdk.components.recipeDetail.success.tag.RecipeDetailSuccessTag
import com.miam.sdk.components.recipeDetail.success.tag.RecipeDetailSuccessTagParameters
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.core.model.RecipeDifficulty
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import kotlin.time.Duration

class CoursesUTags: RecipeDetailSuccessTag {
    @Composable
    override fun Content(params: RecipeDetailSuccessTagParameters) {

        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = params.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Left,
                    style = Typography.subtitleBold
                )
            }
            RecipeDifficultyAndTiming(
                params.difficulty,
                params.preparationTime,
                params.cookingTime,
            )
        }
    }

    @Composable
    fun RecipeDifficultyAndTiming(
        difficulty: RecipeDifficulty,
        preparationTime: Duration?,
        cookingTime: Duration?,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Time(Image.miamPreparation, preparationTime)
            Time(Image.miamCook, cookingTime)
            when (difficulty) {
                RecipeDifficulty.Easy -> RecipeDifficulty(Image.miamDifficulty, Localisation.Recipe.lowDifficulty.localised)
                RecipeDifficulty.Medium -> RecipeDifficulty(Image.miamDifficulty, Localisation.Recipe.mediumDifficulty.localised)
                RecipeDifficulty.Hard -> RecipeDifficulty(Image.miamDifficulty, Localisation.Recipe.highDifficulty.localised)
                else -> {
                    RecipeDifficulty(Image.difficulty, Localisation.Recipe.lowDifficulty.localised)
                }
            }
        }
    }

    @Composable
    fun RecipeDifficulty(imageRef: Int, difficultyLabel: String) {
        Row(
            modifier = Modifier
                .background(shape = RoundedCornerShape(100.dp), color = Color.Transparent)
                .border(border = BorderStroke(1.dp, Colors.lightgrey), shape = RoundedCornerShape(100.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(imageRef),
                contentDescription = "Recipe Difficulty",
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = difficultyLabel,
                style = TextStyle(Colors.boldText, fontSize = 16.sp)
            )
        }
    }

    @Composable
    fun Time(image: Int, time: Duration?) {
        if (time?.inWholeSeconds != 0.toLong()) {
            Row(
                modifier = Modifier
                    .background(shape = RoundedCornerShape(100.dp), color = Color.Transparent,
                    ).border(border = BorderStroke(1.dp, Colors.lightgrey), shape = RoundedCornerShape(100.dp))
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(painter = painterResource(image), contentDescription = "$image", modifier = Modifier.height(20.dp))
                Text(text = "$time", style = TextStyle(Colors.boldText, fontSize = 16.sp))
            }
        }
    }
}