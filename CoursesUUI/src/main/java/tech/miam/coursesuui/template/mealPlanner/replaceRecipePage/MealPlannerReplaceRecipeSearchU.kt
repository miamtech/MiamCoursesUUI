package tech.miam.coursesuui.template.mealPlanner.replaceRecipePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.templateInterfaces.mealPlanner.search.MealPlannerSearch
import com.miam.sdk.templateParameters.mealPlanner.search.MealPlannerSearchParameters

class MealPlannerReplaceRecipeSearchU: MealPlannerSearch {

    @Composable
    override fun Content(budgetSearchParameters: MealPlannerSearchParameters) {

        var currentSearch by remember { mutableStateOf("") }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(Modifier.weight(1f)) {
                    SearchBarText(
                        searchText = currentSearch,
                        onTextChange = {
                            currentSearch = it
                            budgetSearchParameters.updateSearch(it)
                        },
                        submit = { budgetSearchParameters.updateSearch(currentSearch) }
                    )
                }
                Divider(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                IconButton(onClick = budgetSearchParameters.filtersTapped) {
                    Image(
                        painter = painterResource(com.miam.kmm_miam_sdk.android.ressource.Image.filter),
                        contentDescription = "Filter Icon",
                        colorFilter = ColorFilter.tint(Colors.primary)
                    )
                }
            }
            Divider()

        }
    }

    @Composable
    fun SearchBarText(searchText: String, onTextChange: (String) -> Unit, submit: () -> Unit) {
        var isFocused by remember { mutableStateOf(false) }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = onTextChange,
                placeholder = {
                    Text(text = "Que recherchez-vous ?", style = Typography.bodySmall)
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    IconButton(onClick = { isFocused = false }) {
                        Image(
                            painter = painterResource(com.miam.kmm_miam_sdk.android.ressource.Image.search),
                            contentDescription = "Search Icon",
                            colorFilter = ColorFilter.tint(Colors.black)
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { onTextChange("") }) {
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "clear",
                            tint = Colors.grey
                        )
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), // Show Done button on keyboard
                keyboardActions = KeyboardActions(onDone = {
                    isFocused = false
                    submit()
                }), // Call submit function when Done button is pressed
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

        }
    }

}