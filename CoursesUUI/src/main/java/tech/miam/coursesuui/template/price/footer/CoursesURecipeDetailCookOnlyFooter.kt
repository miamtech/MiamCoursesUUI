package tech.miam.coursesuui.template.price.footer

import com.miam.sdk.components.price.footer.success.FooterPriceSuccess
import com.miam.sdk.components.price.footer.success.FooterPriceSuccessParameters
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.components.price.formatPrice

class CoursesURecipeDetailCookOnlyFooter: FooterPriceSuccess {
    @Composable
    override fun Content(params: FooterPriceSuccessParameters) {
        AnimatedVisibility(visible = true,
            enter = slideInVertically { height -> height },
            exit = slideOutVertically { height -> height }) {

            BottomAppBar(Modifier.height(60.dp), backgroundColor = Colors.white) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        params.price.pricePerServe.formatPrice(),
                        color = Colors.black,
                        style = Typography.subtitleBold
                    )
                    Text(
                        Localisation.Price.perGuest.localised,
                        style = Typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Box(Modifier.background(Colors.miamSuccessBackGround, shape = RoundedCornerShape(8.dp))) {
                        Row(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                            Box {
                                Text(
                                    text = Localisation.Price.mealCost(params.price.price.formatPrice()).localised,
                                    style = Typography.bodyBold,
                                    textAlign = TextAlign.Center
                                )
                                Image(
                                    modifier = Modifier
                                        .offset(30.dp, 20.dp)
                                        .width(50.dp),
                                    painter = painterResource(id = Image.trait),
                                    contentDescription = "underline price"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}