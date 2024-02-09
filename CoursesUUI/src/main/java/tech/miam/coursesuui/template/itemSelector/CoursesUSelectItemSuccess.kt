package tech.miam.coursesuui.template.itemSelector

import com.miam.sdk.components.itemSelector.success.ItemSelectorSuccess
import com.miam.sdk.components.itemSelector.success.ItemSelectorSuccessParameters
import androidx.compose.foundation.Image
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.miam.core.localisation.Localisation
import com.miam.core.model.Item
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.sdk.components.price.formatPrice

class CoursesUSelectItemSuccess: ItemSelectorSuccess {

    @Composable
    override fun Content(params: ItemSelectorSuccessParameters) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            params.items.forEach { item -> SelectableItem(item, params.select) }
        }
    }

    @Composable
    private fun SelectableItem(selectableItem: Item, select: (pricedItem: Item) -> Unit) {
        Column {
            Surface(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Image(
                            painter = rememberImagePainter(selectableItem.attributes?.image ?: ""),
                            contentDescription = "Product image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(88.dp)
                                .padding(4.dp)
                                .fillMaxSize()
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            selectableItem.attributes?.name?.let { name ->
                                Text(
                                    text = name.uppercase(),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 18.sp,
                                )
                            }
                            selectableItem.attributes?.itemDescription?.let { itemDescription ->
                                Text(
                                    text = itemDescription,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                            }
                            Badge(selectableItem.capacity)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectableItem.attributes?.unitPrice?.toDouble()?.formatPrice() ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 24.sp,
                            color = Colors.black,
                            textAlign = TextAlign.Center
                        )
                        PrimaryButton(selectableItem, select)
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
    private fun PrimaryButton(selectableItem: Item, select: (item: Item) -> Unit) {
        Surface(shape = RoundedCornerShape(100.dp), color = Colors.primary, modifier = Modifier.clickable { select(selectableItem) }) {
            Text(
                text = Localisation.ItemSelector.select.localised,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp,
                color = Colors.white,
                modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)
            )
        }
    }
}
