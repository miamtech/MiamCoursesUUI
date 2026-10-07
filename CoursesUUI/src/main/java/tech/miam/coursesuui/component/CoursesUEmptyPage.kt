package tech.miam.coursesuui.component

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
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.components.baseComponent.emptyPage.EmptyPage
import ai.mealz.sdk.components.baseComponent.emptyPage.EmptyPageParameters
import ai.mealz.sdk.components.common.Clickable
import ai.mealz.sdk.theme.Typography
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.sp
import tech.miam.coursesuui.R


class CoursesUEmptyPage(private val overrideText: String? = null): EmptyPage {
    @Composable
    override fun Content(params : EmptyPageParameters){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.white)
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_empty),
                    contentDescription = "empty page",
                    Modifier
                        .size(200.dp)
                        .padding(top = 16.dp, bottom = 24.dp)
                )
                Text(
                    text = overrideText ?: params.title,
                    style = Typography.body.copy(fontSize = 16.sp),
                    textAlign = TextAlign.Center,
                    color = Colors.black
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (params.subtitle.isNotEmpty()) {
                    Text(
                        text = params.subtitle,
                        style = Typography.bodyBold.copy(fontSize = 16.sp),
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        color = Colors.black
                    )
                }
                if (params.haveAnAction) {
                    Clickable(onClick = params.action) {
                        Box(
                            Modifier
                                .clip(RoundedCornerShape(50))
                                .background(Colors.primary)
                        ) {
                            Text(
                                text = params.actionText,
                                color = Colors.white,
                                style = Typography.bodyBold.copy(fontSize = 16.sp),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

