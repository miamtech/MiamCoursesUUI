package tech.miam.coursesuui.template.recipeDetail.success

import com.miam.sdk.components.recipeDetail.success.steps.RecipeDetailSteps
import com.miam.sdk.components.recipeDetail.success.steps.RecipeDetailStepsParamters
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography

class CoursesUStep: RecipeDetailSteps {
    @Composable
    override fun Content(params: RecipeDetailStepsParamters) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = Localisation.Recipe.steps.localised,
                style = Typography.subtitleBold,
                color = Colors.black
            )
            params.steps.forEachIndexed { index, recipeStep ->
                recipeStep.attributes?.stepDescription?.let { description ->
                    Step(
                        index,
                        description
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 50.dp))
        }
    }

    @Composable
    fun Step(stepNumber: Int, description: String) {
        Surface(Modifier.clip(RoundedCornerShape(16.dp))) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Surface( shape = CircleShape, color = Colors.primary) {
                    Text(
                        text = (stepNumber + 1).toString(),
                        modifier = Modifier.size(32.dp).padding(top = 6.dp) ,
                        color = Colors.white,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500)
                        )
                    )
                }
                Text(
                    text = description,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1F)
                        .padding(horizontal = 8.dp, vertical = 4.dp)

                )
            }
        }
    }
}