package tech.miam.coursesuui.template.mealPlanner.callToAction

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.miam.coursesuui.R
import ai.mealz.sdk.theme.Typography.body
import ai.mealz.sdk.theme.Typography.bodyBold
import ai.mealz.sdk.components.mealPlanner.callToAction.success.MealPlannerCallToActionSuccess
import ai.mealz.sdk.components.mealPlanner.callToAction.success.MealPlannerCallToActionSuccessParameters

class MealPlannerCallToActionU: MealPlannerCallToActionSuccess {
    @Composable
    override fun Content(params: MealPlannerCallToActionSuccessParameters) {
        Card(modifier = Modifier
            .padding(horizontal = 12.dp)
            .clickable { params.onclick() }) {
            Box(contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.ic_blue_wave), contentDescription = null, Modifier.offset(y = 36.dp))
                Image(
                    painter = painterResource(id = R.drawable.meals_image_u), contentDescription = null,
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillWidth
                )
                Box {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_budget_h),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .padding(horizontal = 48.dp)
                        )
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .padding(top = 4.dp, bottom = 16.dp),
                            border = BorderStroke(1.dp, Color(R.color.miam_courses_u_primary))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
                            ) {
                                Text(text = "Renseignez vos critères", style = body.copy(fontSize = 12.sp))
                                Row {
                                    Text(text = "et sélectionnez ", style = body.copy(fontSize = 12.sp))
                                    Text(text = "vos idées repas préférées !", style = bodyBold.copy(fontSize = 12.sp))
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.padding(top = 12.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_price_u),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .padding(end = 4.dp)
                                    )
                                    Text(text = "35 €", style = bodyBold.copy(fontSize = 10.sp))
                                    Divider(
                                        thickness = 1.dp, color = Color(R.color.miam_courses_u_primary), modifier = Modifier
                                            .padding(horizontal = 20.dp)
                                            .height(16.dp)
                                            .width(1.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_guests_u),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .padding(end = 4.dp)
                                    )
                                    Text(text = "4", style = bodyBold.copy(fontSize = 10.sp))
                                    Divider(
                                        thickness = 1.dp, color = Color(R.color.miam_courses_u_primary), modifier = Modifier
                                            .padding(horizontal = 20.dp)
                                            .height(16.dp)
                                            .width(1.dp)
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_meals_u),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .padding(end = 4.dp)
                                    )
                                    Text(text = "3", style = bodyBold.copy(fontSize = 10.sp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}