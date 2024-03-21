package tech.miam.coursesuui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.ressource.Image.trait
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import ai.mealz.sdk.theme.Typography.bodyBold
import ai.mealz.sdk.components.mealPlanner.basketPreview.footer.MealPlannerBasketPreviewFooter
import ai.mealz.sdk.components.mealPlanner.basketPreview.footer.MealPlannerBasketPreviewFooterParameters
import ai.mealz.sdk.components.mealPlanner.meals.footer.MealPlannerFooter
import ai.mealz.sdk.components.mealPlanner.meals.footer.MealsFooterParameters

import java.text.NumberFormat
import tech.miam.coursesuui.R
import java.util.*

class CoursesUMealPlannerFooter: MealPlannerFooter, MealPlannerBasketPreviewFooter {
    @Composable
    override fun Content(params: MealsFooterParameters) {
        FooterBase(
            totalPrice = params.recipesPrice,
            progressRatio = params.recipesPrice / params.budget,
            progressDelta = params.recipesPrice - params.budget,
            action = { params.addAllToBasket() }
        ) {
            Icon(
                painterResource(id = Image.cart),
                contentDescription = null,
                tint = Colors.white,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Tout ajouter",
                color = Color.White,
                style = Typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    override fun Content(params: MealPlannerBasketPreviewFooterParameters) {
        FooterBase(
            totalPrice = params.totalPrice,
            progressRatio = params.totalPrice / params.budget,
            progressDelta = params.totalPrice - params.budget,
            action = { params.confirmAction() }
        ) {
            Text(
                text = "Finaliser",
                color = Color.White,
                style = Typography.body
            )
        }
    }

    @Composable
    private fun FooterBase(
        totalPrice: Double,
        progressRatio: Double,
        progressDelta: Double,
        action: () -> Unit,
        Content: @Composable () -> Unit
    ) {
        AnimatedVisibility(
            visible = totalPrice != 0.0,
            enter = slideInVertically { height -> height },
            exit = slideOutVertically { height -> height },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(start = 14.dp, end = 28.dp)
                ) {
                    Progressbar(progressRatio, progressDelta)
                    Price(totalPrice)
                }
                FooterAction(action) {
                    Content()
                }
            }
        }
    }

    @Composable
    private fun Progressbar(ratio: Double, priceDelta: Double) {

        NumberFormat.getCurrencyInstance().currency = Currency.getInstance(Localisation.Price.currency.localised)
        val isBudgetExceeded = ratio > 1

        val progressAnimation = animateFloatAsState(
            targetValue = if (ratio > 1) 0.80.toFloat()
            else (ratio).toFloat(),
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        ).value

        if (isBudgetExceeded) {
            PriceExceededInfo(priceDelta)
        }
        LinearProgressIndicator(
            backgroundColor = if (isBudgetExceeded) colorResource(R.color.miam_courses_u_text_alert) else colorResource(R.color.miam_courses_u_primary),
            progress = progressAnimation,
            modifier = Modifier
                .width(182.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = colorResource(R.color.miam_courses_u_primary_light)
        )
    }

    @Composable
    private fun PriceExceededInfo(priceDelta: Double) {
        val miamCoursesUAlertColor = colorResource(id = R.color.miam_courses_u_alert)
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(miamCoursesUAlertColor)
            ) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "+${NumberFormat.getCurrencyInstance().format(priceDelta)}",
                    style = bodyBold.copy(color = colorResource(R.color.miam_courses_u_text_alert), fontSize = 10.sp)
                )
            }
            Canvas(modifier = Modifier
                .height(8.dp)
                .width(12.dp)
                .padding(end = 4.dp)
                .rotate(180F),
                onDraw = {
                    val trianglePath = Path().apply {
                        moveTo(size.width / 2f, 0f)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                    }
                    drawPath(
                        color = miamCoursesUAlertColor,
                        path = trianglePath
                    )
                })
        }
    }

    @Composable
    private fun FooterAction(action: () -> Unit, Content: @Composable () -> Unit) {
        Button(
            onClick = { action() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(end = 24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primary),
            shape = RoundedCornerShape(32.dp)
        ) {
            Content()
        }
    }

    @Composable
    private fun Price(price: Double) {
        Column {
            Text(
                text = NumberFormat.getCurrencyInstance().format(price),
                color = Colors.black,
                style = bodyBold.copy(fontSize = 18.sp)
            )
            Image(
                painter = painterResource(id = trait),
                contentDescription = null,
                Modifier.width(54.dp)
            )
        }
    }

}

