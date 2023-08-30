package tech.miam.coursesuui.template.mealPlanner.form

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import clearFocusOnKeyboardDismiss
import com.miam.kmm_miam_sdk.android.theme.Typography

@Composable
fun CoursesUCurrency(text: String) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier
                .width(48.dp)
                .wrapContentHeight(),
            text = text,
            textAlign = TextAlign.Center,
            style = Typography.subtitleBold
        )
    }
}

@Composable
fun CoursesUBudgetInt(budgetAmount: Int, onBudgetChanged: (Int) -> Unit) {

    var text by remember { mutableStateOf(TextFieldValue(budgetAmount.toString()))}
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isFocused) {
        text = text.copy(
            selection = if (isFocused) {
                TextRange(
                    start = 0,
                    end = text.text.length
                )
            } else {
                TextRange.Zero
            }
        )
    }

    BasicTextField(
        value = text,
        interactionSource = interactionSource,
        onValueChange = { newValue ->
            if (newValue.text.length <= 5 && newValue.text.matches(Regex("[0-9]*"))) {
                text = newValue
            }
        },
        modifier = Modifier
            .defaultMinSize(minHeight = 40.dp)
            .fillMaxWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(end = 30.dp)
            .onFocusChanged { onBudgetChanged(text.text.toIntOrNull() ?: 0) }
            .onKeyEvent { event ->
                if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                    focusManager.clearFocus()
                    true
                } else {
                    false
                }
            }
            .clearFocusOnKeyboardDismiss(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
        ),
        singleLine = true,
        textStyle = Typography.subtitleBold.copy(textAlign = TextAlign.End),
    )
}
