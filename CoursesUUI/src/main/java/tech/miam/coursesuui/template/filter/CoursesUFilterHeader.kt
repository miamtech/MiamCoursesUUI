package tech.miam.coursesuui.template.filter

import ai.mealz.sdk.components.baseComponent.filter.success.header.FilterSuccessHeader
import ai.mealz.sdk.components.baseComponent.filter.success.header.FilterSuccessHeaderParameters
import ai.mealz.sdk.components.baseComponent.likeButton.LikeButton
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tech.miam.coursesuui.R
import tech.miam.coursesuui.component.CoursesUMealzHeader


class CoursesUFilterHeader : FilterSuccessHeader {
    @Composable
    override fun Content(params: FilterSuccessHeaderParameters) {
        CoursesUMealzHeader(
            params.title,
            params.close
        )
    }
}