package tech.miam.coursesuui.template.myMeal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.sdk.components.baseComponent.myMealButton.success.MyMealButtonSuccess
import com.miam.sdk.components.baseComponent.myMealButton.success.MyMealButtonSuccessParameters

class CoursesUMyMealButton: MyMealButtonSuccess {
    @Composable
    override fun Content(params: MyMealButtonSuccessParameters) {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically { height -> height },
            exit = slideOutVertically { height -> height }
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                shape = RoundedCornerShape( 50 ,50),
                color = Colors.primary,
                contentColor = Colors.white
            ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { params.onClick() }
                    .padding(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = Image.cart),
                    contentDescription = "icon categories page floating",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(16.dp)
                )
                Text(
                    text = Localisation.MyMeals.mealsInBasket(params.recipeCount).localised,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(600),
                    ),
                )
                    Icon(
                        Icons.Filled.KeyboardArrowRight,
                        contentDescription = "icon button categories page floating",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(16.dp)
                    )
            }
        }
        }
    }
}
