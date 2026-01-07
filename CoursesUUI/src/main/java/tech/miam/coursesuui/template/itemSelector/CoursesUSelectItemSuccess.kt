package tech.miam.coursesuui.template.itemSelector

import ai.mealz.core.helpers.formatPrice
import ai.mealz.sdk.components.itemSelector.success.ItemSelectorSuccess
import ai.mealz.sdk.components.itemSelector.success.ItemSelectorSuccessParameters
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.components.itemSelector.success.ItemSelectorSuccessItem
import ai.mealz.sdk.theme.Colors
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import tech.miam.coursesuui.R

class CoursesUSelectItemSuccess: ItemSelectorSuccess {

    @Composable
    override fun Content(params: ItemSelectorSuccessParameters) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            params.itemSelectorItems.forEach { item ->
                SelectableItem(item, params.isASubstitution)
            }
        }
    }

    @Composable
    private fun SelectableItem(
        selectableItem: ItemSelectorSuccessItem,
        isASubstitution: Boolean
    ) {
        Column {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        AsyncImage(
                            model = selectableItem.imageUrl,
                            contentDescription = "Product image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(88.dp)
                                .padding(4.dp)
                                .fillMaxSize()
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = selectableItem.brand.uppercase(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.mealz_mullish))
                            )
                            Text(
                                text = selectableItem.name,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                            Badge(selectableItem.packaging)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectableItem.price.formatPrice(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 24.sp,
                            color = Colors.black,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.mealz_mullish))
                        )
                        PrimaryButton(selectableItem, isASubstitution)
                    }
                }
            }
            Surface(color = Colors.border, modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.size(1.dp))
            }
        }
    }

    @Composable
    private fun Badge(text: String) {
        Surface(
            color = Colors.backgroundSecondary,
            modifier = Modifier.padding(top = 8.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = text, modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        }
    }

    @Composable
    private fun PrimaryButton(
        selectableItem: ItemSelectorSuccessItem,
        isASubstitution: Boolean
    ) {
        Surface(
            shape = RoundedCornerShape(100.dp),
            color = Colors.primary,
            modifier = Modifier.clickable { selectableItem.select() }
        ) {
            Text(
                text = if (isASubstitution) Localisation.itemSelector.replace.localised else Localisation.itemSelector.add.localised,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp,
                color = Colors.white,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)
            )
        }
    }
}
