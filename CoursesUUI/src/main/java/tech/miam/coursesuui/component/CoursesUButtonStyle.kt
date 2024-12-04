package tech.miam.coursesuui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ai.mealz.sdk.theme.Dimension
import tech.miam.coursesuui.R
import tech.miam.coursesuui.template.mealPlanner.form.MealCountState

@Composable
fun CoursesUButton(
    counterState: MealCountState,
    backgroundColor: Color = Color(R.color.miam_courses_u_primary),
    buttonStrokeColor: Color = Color.Transparent,
    enabled: Boolean = true,
    cornerRadius: Dp = Dimension.lRoundedCorner.dp,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = Dimension.lPadding,
        vertical = Dimension.mPadding
    ),
    content: @Composable RowScope.() -> Unit,
    buttonAction: () -> Unit,
) {
    val shape = RoundedCornerShape(cornerRadius)
    Button(
        onClick = { buttonAction() },
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = shape,
        enabled = counterState.count > 0 && enabled,
        border = BorderStroke(width = Dp(1f), color = buttonStrokeColor),
        contentPadding = paddingValues,
        content = content,
        modifier = Modifier
            .height(54.dp)
            .width(180.dp)
    )
}

@Preview
@Composable
fun CoursesUButtonPreview() {
    MaterialTheme {
        Column() {
            CoursesUButton(
                MealCountState(),
                buttonAction = { /* Your action here */ },
                content =
                {
                    Text(text = "My Button")
                })
            CoursesUButton(
                MealCountState(),
                backgroundColor = Color.Red,
                cornerRadius = Dimension.sRoundedCorner,
                buttonAction = { /* Your action here */ },
                content =
                {
                    Text(text = "My Button")
                })
            CoursesUButton(
                MealCountState(),
                backgroundColor = Color.Gray,
                buttonStrokeColor = Color.Green,
                buttonAction = { /* Your action here */ },
                content =
                {
                    Text(text = "My Button")
                })
        }
    }
}