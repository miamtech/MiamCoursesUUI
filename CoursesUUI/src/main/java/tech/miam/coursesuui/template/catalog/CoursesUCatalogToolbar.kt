package tech.miam.coursesuui.template.catalog

import ai.mealz.sdk.components.catalog.success.toolbar.CatalogSuccessToolbar
import ai.mealz.sdk.components.catalog.success.toolbar.CatalogSuccessToolbarParameters
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ai.mealz.core.localisation.Localisation
import ai.mealz.core.viewModels.catalog.CatalogContent
import ai.mealz.sdk.di.TemplateDI
import ai.mealz.sdk.ressource.Image
import ai.mealz.sdk.ressource.Image.filter
import ai.mealz.sdk.ressource.Image.gear
import ai.mealz.sdk.ressource.Image.mySpaceIcon
import ai.mealz.sdk.ressource.Image.previous
import ai.mealz.sdk.ressource.Image.search
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Colors.backgroundSecondary
import ai.mealz.sdk.theme.Colors.boldText
import ai.mealz.sdk.theme.Dimension.lIconHeight
import ai.mealz.sdk.theme.Dimension.lPadding
import ai.mealz.sdk.theme.Dimension.mPadding
import ai.mealz.sdk.theme.Dimension.mRoundedCorner
import ai.mealz.sdk.theme.Dimension.sSpacerWidth
import ai.mealz.sdk.theme.Typography
import ai.mealz.sdk.theme.Typography.bodyBold
import ai.mealz.sdk.theme.Typography.bodySmall
import ai.mealz.sdk.theme.Typography.title
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import tech.miam.coursesuui.R
/*
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
                    Text(text = "Catalogue", color = Colors.white,
                        style = Typography.subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))))
                } else {
                    Text(text = Localisation.catalog.title.localised, color = Colors.white, style = Typography.subtitleBold.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))))
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
                            modifier = Modifier.graphicsLayer(scaleX = -1f) // flip direction
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
}*/



class CoursesUCatalogToolbar : CatalogSuccessToolbar {
    @Composable
    override fun Content(params: CatalogSuccessToolbarParameters) {
        val showFullHeight =
            params.filterAreEnable || params.content == CatalogContent.CATEGORIES_LIST
        if (params.elementHeight > params.elementMinHeight || params.content == CatalogContent.FAVORITE) {
            Column(
                Modifier
                    .then(
                        if (showFullHeight) Modifier
                            .height(params.elementHeight)
                            .offset { params.scrollOffset } else Modifier
                    )
            ) {
                Box(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.padding(lPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (params.content != CatalogContent.CATEGORIES_LIST) {
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(mRoundedCorner))
                                    .background(backgroundSecondary)
                                    .clickable { params.goToBack() }
                            ) {
                                Image(
                                    painter = painterResource(previous),
                                    contentDescription = "previous",
                                    modifier = Modifier.padding(mPadding)
                                )
                            }
                            Spacer(modifier = Modifier.width(sSpacerWidth))
                        }
                        Column {
                            Text(
                                text = if (params.currentSearchString?.isNotBlank() == true
                                ) "\"${params.currentSearchString}\"" else params.currentPackageTitle
                                    ?: Localisation.mySpace.title.localised,
                                style = title.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                            )
                            params.currentSubtitle?.let {
                                Text(
                                    text = it,
                                    style = bodySmall.copy(fontFamily = FontFamily(Font(R.font.mealz_mullish))),
                                    color = Colors.disabledText
                                )
                            }
                        }
                    }
                }
                if (showFullHeight) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(mPadding),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (params.filterAreEnable) {
                            Tags(
                                Localisation.catalog.preferencesTitle.localised,
                                gear,
                                params.getActivePreferenceCount(),
                                Modifier.weight(1.3f),
                                params.openPreferences
                            )
                        }
                        if (params.content == CatalogContent.CATEGORIES_LIST) {
                            Tags(
                                Localisation.catalog.favoriteTitle.localised,
                                mySpaceIcon,
                                0,
                                Modifier
                                    .weight(1f)
                                    .padding(start = mPadding),
                                params.goToFavorite
                            )
                        }
                    }
                }
                Divider(Modifier.fillMaxWidth())
                BottomToolbar(
                    params.getActiveFilterCount(),
                    params.getActivePreferenceCount(),
                    params.content,
                    false,
                    params.goToBack,
                    params.goToFavorite,
                    params.openPreferences,
                    params.openFilter
                ) { params.openSearch() }
            }

        } else {
            BottomToolbar(
                params.getActiveFilterCount(),
                params.getActivePreferenceCount(),
                params.content,
                true,
                params.goToBack,
                params.goToFavorite,
                params.openPreferences,
                params.openFilter
            ) { params.openSearch() }
        }
    }


    @Composable
    private fun BottomToolbar(
        activeFilterNumber: Int,
        activePreferencesNumber: Int,
        content: CatalogContent,
        isCollapse: Boolean = false,
        goToBack: () -> Unit,
        goToFavorite: () -> Unit,
        openPreferences: () -> Unit,
        openFilter: () -> Unit,
        openSearch: () -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(mPadding)
        ) {

            Row {
                if (isCollapse && content != CatalogContent.CATEGORIES_LIST) {
                    Surface(
                        shape = CircleShape,
                        elevation = 0.dp,
                        modifier = Modifier
                            .padding(mPadding)
                            .clickable { goToBack() }
                    ) {
                        Image(
                            painter = painterResource(previous),
                            contentDescription = "previous",
                            colorFilter = ColorFilter.tint(boldText),
                        )
                    }
                }
                if (content != CatalogContent.FAVORITE) {
                    Surface(
                        shape = CircleShape,
                        elevation = 0.dp,
                        modifier = Modifier
                            .padding(mPadding)
                            .clickable { openSearch() }
                    ) {
                        Image(
                            painter = painterResource(search),
                            contentDescription = "search",
                            colorFilter = ColorFilter.tint(boldText),
                        )
                    }
                }
            }
            if (content != CatalogContent.FAVORITE) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    //if (TemplateDI.catalog.success.toolbar.enableFilters) {
                        IconWithBadge(activeFilterNumber, filter, openFilter)
                    //}
                    if (isCollapse) {
                        /*if (TemplateDI.catalog.success.toolbar.enablePreferences) {
                            IconWithBadge(activePreferencesNumber, gear, openPreferences)
                        }*/
                        if (content == CatalogContent.CATEGORIES_LIST) {
                            Surface(
                                shape = CircleShape,
                                elevation = 0.dp,
                                modifier = Modifier
                                    .padding(mPadding)
                                    .clickable { goToFavorite() }
                            ) {
                                Image(
                                    painter = painterResource(mySpaceIcon),
                                    contentDescription = "My Space",
                                    colorFilter = ColorFilter.tint(boldText),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Tags(
        text: String,
        icon: Int,
        activeNumber: Int = 0,
        modifier: Modifier,
        action: () -> Unit
    ) {
        Surface(
            shape = CircleShape,
            color = backgroundSecondary,
            elevation = 0.dp,
            modifier = modifier.clickable { action() }
        ) {
            Row(
                Modifier.padding(horizontal = lPadding, vertical = lPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Image(
                    painter = painterResource(icon),
                    contentDescription = "tag icon",
                    colorFilter = ColorFilter.tint(boldText),
                )
                Spacer(modifier = Modifier.width(mPadding))
                Text(
                    text = text,
                    style = bodyBold,
                    color = boldText
                )

                if (activeNumber != 0) {
                    Spacer(modifier = Modifier.width(mPadding))
                    Box(
                        modifier = Modifier
                            .size(lIconHeight)
                            .clip(CircleShape)
                            .background(Colors.black)
                    ) {
                        Text(
                            text = activeNumber.toString(),
                            color = backgroundSecondary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun IconWithBadge(activeNumber: Int, icon: Int, action: () -> Unit) {
        Box(Modifier.clickable { action() }) {
            Surface(
                shape = CircleShape,
                modifier = Modifier.padding(mPadding)
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = "icon",
                    colorFilter = ColorFilter.tint(boldText),
                )
            }
            if (activeNumber != 0) {
                Box(
                    modifier = Modifier
                        .size(lIconHeight)
                        .clip(CircleShape)
                        .background(Colors.black)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = activeNumber.toString(),
                        color = Colors.white,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}