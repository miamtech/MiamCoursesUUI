package tech.miam.coursesuui.template.catalog.categoryPage

import androidx.compose.foundation.Image
import com.miam.sdk.components.catalog.success.categoriesPage.category.CatalogCategoriesPageCategory
import com.miam.sdk.components.catalog.success.categoriesPage.category.CatalogCategoriesPageCategoryParameters
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.miam.core.localisation.Localisation
import com.miam.core.model.Recipe
import com.miam.kmm_miam_sdk.android.ressource.Image.previous
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.sdk.components.recipeJourney.RecipeJourney

class CoursesUCatalogCategory: CatalogCategoriesPageCategory {
    @Composable
    override fun Content(param: CatalogCategoriesPageCategoryParameters) {
        Column (verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Column {
                    param.category.attributes?.title?.let {
                        Text(
                            text = it,
                            color = Colors.black,
                            style = Typography.subtitleBold
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth()
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        param.category.subtitle?.let {
                            Text(
                                text = it,
                                color = Colors.black,
                                modifier = Modifier.weight(1f)
                            )
                        } ?: Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { param.goToCategoryPage(param.category) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = Localisation.Catalog.showAll.localised,
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = Colors.primary,
                            )
                            Image(
                                painter = painterResource(previous),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Colors.primary),
                                modifier = Modifier
                                    .rotate(180f)
                                    .padding(vertical = 8.dp).padding(end = 8.dp)
                            )
                        }
                    }
                }
            }

            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(
                    key = { item: Recipe -> item.id },
                    items = (param.category.relationships?.recipes?.data ?: emptyList())
                ) { recipe ->
                    Box(
                        modifier = Modifier
                            .width(240.dp)
                            .height(330.dp)
                    ) {
                        RecipeJourney.View(recipe = recipe)
                    }
                }
            }
        }
    }
}