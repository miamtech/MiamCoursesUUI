package tech.miam.coursesuui.template.itemSelector

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.sdk.components.itemSelector.search.ItemSelectorSearch
import com.miam.sdk.components.itemSelector.search.ItemSelectorSearchParameters
import tech.miam.coursesuui.R
import java.util.Locale

class CoursesUItemSelectorSearch : ItemSelectorSearch {
    @Composable
    override fun Content(params: ItemSelectorSearchParameters) {
        Column(Modifier.padding(vertical = 24.dp, horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SearchBar(params)
            IngredientInfo(params)
        }
    }

    @Composable
    private fun SearchBar(params: ItemSelectorSearchParameters) {
        var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Colors.backgroundSecondary,
            border = BorderStroke(1.dp, Colors.grey),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                leadingIcon = { Icon( painterResource(Image.search), "Search icon") },
                trailingIcon = {
                    if (textFieldValue.text.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Close, "Close icon",
                            Modifier.clickable { textFieldValue = textFieldValue.copy(text = "") }
                        )
                    }
                },
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                    params.onChanges(it.text)
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        Localisation.ItemSelector.search.localised,
                        style = TextStyle(fontSize = 16.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun IngredientInfo(params: ItemSelectorSearchParameters) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Colors.backgroundSecondary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "${params.ingredientName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT) else it.toString() }} :",
                    style = TextStyle(fontSize = 14.sp)
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "${params.ingredientQuantity} ${params.ingredientUnit}",
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Black)
                )
            }
        }
    }
}