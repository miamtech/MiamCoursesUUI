package tech.miam.coursesuui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

import com.miam.kmm_miam_sdk.android.theme.Typography
import tech.miam.coursesuui.R

@Composable
fun YellowSubtext(
    text: String,
    fontStyle: TextStyle = Typography.bodyBold,
    imageWidth: Dp = 45.dp,
    imageHeight: Dp = 8.dp
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = text,
            color = Color.Black,
            style = fontStyle,
            modifier = Modifier.zIndex(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.yellow_underline),
            contentDescription = "Yellow underline",
            modifier = Modifier
                .offset(y  =-imageHeight * 5/6)
                .zIndex(0f)
                .size(imageWidth, imageHeight)
        )
    }
}

@Preview
@Composable
fun YellowSubtextPreview() {
    Column {
        YellowSubtext(text = "14,96€")
        YellowSubtext(text = "15,33", fontStyle = Typography.subtitle) // Replace with your TextStyle
        YellowSubtext(text = "12,56", fontStyle = Typography.subtitleBold, imageWidth = 60.dp)
    }
}
