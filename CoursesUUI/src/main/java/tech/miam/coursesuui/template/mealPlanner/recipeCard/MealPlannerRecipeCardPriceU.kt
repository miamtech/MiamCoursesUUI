package tech.miam.coursesuui.template.mealPlanner.recipeCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.miam.core.localisation.Localisation
import tech.miam.coursesuui.theme.Typography
import com.miam.kmm_miam_sdk.android.ressource.Image.trait
import java.text.NumberFormat
import java.util.*

@Composable
fun RecipeCardPrice(price: Double) {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.currency = Currency.getInstance(Localisation.Price.currency.localised)
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.height(22.dp)
        ) {
            Image(
                painter = painterResource(id = trait),
                contentDescription = null,
                modifier = Modifier
                    .offset(y = 18.dp)
                    .width(40.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = numberFormat.format(price),
                style = Typography.title
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text("le repas", style = Typography.body)
    }
}