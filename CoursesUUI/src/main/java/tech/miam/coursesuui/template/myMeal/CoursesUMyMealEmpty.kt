package tech.miam.coursesuui.template.myMeal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.kmm_miam_sdk.android.ui.components.common.Clickable
import com.miam.sdk.components.baseComponent.emptyPage.EmptyPage
import com.miam.sdk.components.baseComponent.emptyPage.EmptyPageParameters
import tech.miam.coursesuui.R

class CoursesUMyMealAndFavoritesEmpty: EmptyPage {
    @Composable
    override fun Content(params : EmptyPageParameters){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.primary)
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.empty_white),
                    contentDescription = "empty page",
                    Modifier.padding(top = 16.dp, bottom = 24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Text(
                    text = "Vous n'avez aucune idées repas",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (params.haveAnAction) {
                    Clickable(onClick = params.action) {
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(50))
                                .background(colorResource(id = R.color.button_background))
                        ) {
                            Text(
                                text = params.actionText,
                                color = Colors.white,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

