package tech.miam.coursesuui.template.filter

import ai.mealz.core.localisation.Localisation
import ai.mealz.sdk.components.baseComponent.filter.success.footer.FilterSuccessFooter
import ai.mealz.sdk.components.baseComponent.filter.success.footer.FilterSuccessFooterParameters
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class CoursesUFilterFooter : FilterSuccessFooter {
    @Composable
    override fun Content(params: FilterSuccessFooterParameters) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Colors.white)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100.dp))
                    .background(Colors.primary)
                    .clickable { params.applyFilter() },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = Localisation.catalog.showResults(params.numberOfResult).localised,
                    style = Typography.subtitle.copy(fontWeight = FontWeight.Bold),
                    color = Colors.white
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100.dp))
                    .clickable { params.clearFilter() },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = Localisation.catalog.removeFilters.localised,
                    style = Typography.subtitle.copy(fontWeight = FontWeight.Bold),
                    color = Colors.primary
                )
            }
        }
    }
}