package tech.miam.coursesuui.template.catalog

import com.miam.sdk.components.catalog.success.toolbar.CatalogSuccessToolbar
import com.miam.sdk.components.catalog.success.toolbar.CatalogSuccessToolbarParameters
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miam.core.localisation.Localisation
import com.miam.core.viewModels.catalog.CatalogContent
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.kmm_miam_sdk.android.ui.components.common.Clickable
import tech.miam.coursesuui.R

class CoursesUCatalogToolbar: CatalogSuccessToolbar {
    @Composable
    override fun Content(params: CatalogSuccessToolbarParameters) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = Colors.primary)
                    .padding(horizontal = 8.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                if (params.content != CatalogContent.CATEGORIES_LIST) {

                    IconButton(onClick = { params.goToBack() }) {
                        Image(
                            painter = painterResource(Image.toggleCaret),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Colors.white),
                            modifier = Modifier
                                .rotate(180f)
                                .padding(vertical = 8.dp).padding(end = 8.dp)
                        )
                    }
                    Text(text = "Catalogue", color = Colors.white, style = Typography.subtitleBold,)
                } else {
                    Text(text = Localisation.Catalog.title.localised, color = Colors.white, style = Typography.subtitleBold,)
             }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    IconButton(onClick = { params.openSearch() }) {
                        Image(
                            painter = painterResource(Image.search),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Colors.primary),
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { params.openFilter() }) {
                        Box {
                            Image(
                                painter = painterResource(Image.filter),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Colors.primary),
                            )
                            if (params.getActiveFilterCount() != 0) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red)
                                        .align(Alignment.TopEnd)
                                ) {
                                    Text(
                                        text = params.getActiveFilterCount().toString(),
                                        color = Colors.white,
                                        fontSize = 10.sp,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                    if (params.content == CatalogContent.CATEGORIES_LIST) {
                        IconButton(onClick = { params.goToFavorite() }) {
                            Image(
                                painter = painterResource(R.drawable.heart),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Colors.primary),
                            )
                        }
                    }
                }
            }
            Divider(Modifier.fillMaxWidth())
        }
    }
}