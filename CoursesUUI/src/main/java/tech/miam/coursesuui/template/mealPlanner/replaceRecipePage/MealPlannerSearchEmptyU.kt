package tech.miam.coursesuui.template.mealPlanner.replaceRecipePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.kmm_miam_sdk.android.theme.Typography.body
import com.miam.kmm_miam_sdk.android.theme.Typography.bodyBold
import tech.miam.coursesuui.R
import com.miam.sdk.templateInterfaces.mealPlanner.search.MealPlannerSearchEmpty
import com.miam.sdk.templateParameters.mealPlanner.search.MealPlannerSearchEmptyParameters

class MealPlannerSearchEmptyU: MealPlannerSearchEmpty {
    @Composable
    override fun Content(mealPlannerSearchEmptyParameters: MealPlannerSearchEmptyParameters) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "0 idée repas", modifier = Modifier.padding(top = 150.dp, bottom = 20.dp), style = bodyBold.copy(fontSize = 18.sp)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = Color(0xFFFFE4D0))
                    .fillMaxWidth()
                    .height(68.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_warning_u),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                    )
                    Text(text = "Désolé, il n'y a pas d'idée repas\ncorrespondant à cette recherche.", style = body.copy(fontSize = 14.sp))
                }
            }
        }
    }
}