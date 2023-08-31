package tech.miam.coursesuui.template.mealPlanner.recap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.miam.coursesuui.R
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.kmm_miam_sdk.android.ui.components.price.formatPrice
import com.miam.sdk.templateInterfaces.mealPlanner.recap.MealPlannerRecap
import com.miam.sdk.templateParameters.mealPlanner.recap.MealPlannerRecapParameters

class MealPlannerRecapU: MealPlannerRecap {
    @Composable
    override fun Content(mealPlannerRecapParameters: MealPlannerRecapParameters) {
        Box(
            modifier = Modifier
                .background(Color(0xFFBFDDE8))
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_wave), contentDescription = null, modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-268).dp)
            )
            Image(
                painter = painterResource(id = R.drawable.meal_2), contentDescription = null, modifier = Modifier
                    .offset(x = 134.dp, y = 460.dp)
                    .width(240.dp)
                    .height(224.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.meal_1), contentDescription = null, modifier = Modifier
                    .offset(x = (-100).dp, y = 396.dp)
                    .width(420.dp)
                    .height(308.dp)
            )
            Column {
                Image(
                    painter = painterResource(id = R.drawable.logo_budget_h),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                )
                Card(modifier = Modifier.padding(24.dp), shape = RoundedCornerShape(10.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 24.dp, horizontal = 20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_green_check),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Text(
                            lineHeight = 24.sp,
                            text = "Tous les ingrédients ont bien été ajoutés au panier.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            style = Typography.subtitleBold,
                            textAlign = TextAlign.Center,
                            color = Colors.black,
                        )
                        GreenRecapComponent(mealPlannerRecapParameters.numberOfMeals, mealPlannerRecapParameters.totalPrice)
                        Divider(modifier = Modifier.padding(vertical = 30.dp))
                        Text(
                            text = "Découvrez aussi :",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            style = Typography.subtitleBold,
                            textAlign = TextAlign.Center,
                            color = Colors.black
                        )
                        Button(
                            onClick = { mealPlannerRecapParameters.action() },
                            modifier = Modifier
                                .width(162.dp)
                                .height(40.dp),
                            shape = RoundedCornerShape(40.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE22019))
                        ) {

                            Text(
                                text = "Nos promotions",
                                color = Colors.white,
                                style = Typography.bodyBold.copy(fontSize = 14.sp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun GreenRecapComponent(mealCount: Int, price: Double) {
        Row(
            Modifier
                .height(44.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFD4F8DD)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$mealCount repas pour", style = Typography.body)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = formatPrice(price), style = Typography.subtitleBold)
                Image(
                    painter = painterResource(id = R.drawable.ic_underline),
                    contentDescription = null,
                    modifier = Modifier
                        .width(60.dp)
                        .height(4.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}