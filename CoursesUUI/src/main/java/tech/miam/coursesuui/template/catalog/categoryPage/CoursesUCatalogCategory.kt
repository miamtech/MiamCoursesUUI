package tech.miam.coursesuui.template.catalog.categoryPage

import ai.mealz.core.localisation.Localisation
import ai.mealz.core.model.Recipe
import ai.mealz.sdk.components.catalog.success.categoriesPage.category.CatalogCategoriesPageCategory
import ai.mealz.sdk.components.catalog.success.categoriesPage.category.CatalogCategoriesPageCategoryParameters
import ai.mealz.sdk.components.recipeJourney.RecipeJourney
import ai.mealz.sdk.ressource.Image.toggleCaret
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import tech.miam.coursesuui.R

class CoursesUCatalogCategory : CatalogCategoriesPageCategory {
    @Composable
    override fun Content(param: CatalogCategoriesPageCategoryParameters) {
        Column(
            Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
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
                            style = Typography.subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish)))
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        param.category.subtitle?.let {
                            Text(
                                text = it,
                                color = Colors.black,
                                modifier = Modifier.weight(1f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = TextStyle(fontFamily = FontFamily(Font(R.font.mealz_mullish)))
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
                                text = Localisation.catalog.showAll.localised,
                                color = Colors.primary,
                            )
                            Image(
                                painter = painterResource(toggleCaret),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Colors.primary),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                items(
                    key = { item: Recipe -> item.id },
                    items = (param.category.relationships?.recipes?.data ?: emptyList())
                ) { recipe ->
                    Box(
                        modifier = Modifier
                            .width(240.dp)
                            .height(330.dp)
                    ) {
                        RecipeJourney.View(recipe = recipe, isInShelve = false)
                    }
                }
            }
        }
    }
}