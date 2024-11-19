package tech.miam.coursesuui.template.itemSelector

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ai.mealz.sdk.components.baseComponent.emptyPage.EmptyPage
import ai.mealz.sdk.components.baseComponent.emptyPage.EmptyPageParameters
import tech.miam.coursesuui.R

class CoursesUItemSelectorEmpty: EmptyPage {
    @Composable
    override fun Content(params: EmptyPageParameters) {

            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.ic_empty), contentDescription = null, Modifier.padding(top = 16.dp, bottom = 24.dp))
                Text(
                    text = stringResource(id = R.string.item_selector_no_result),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text =  stringResource(id = R.string.item_selector_try_again),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                    ),
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
    }
}