package tech.miam.coursesuui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ai.mealz.sdk.ressource.Image.miamEmpty
import ai.mealz.sdk.theme.Colors
import ai.mealz.sdk.theme.Typography
import ai.mealz.sdk.components.baseComponent.emptyPage.EmptyPage
import ai.mealz.sdk.components.baseComponent.emptyPage.EmptyPageParameters


 class EmptyPage : EmptyPage {
    @Composable()
    override fun Content(params : EmptyPageParameters){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.primary)
        ) {
            Column(
                Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource( miamEmpty),
                    contentDescription = null,
                    Modifier.padding(vertical = 16.dp)
                )
                Text(
                    text = "Vous n’avez aucune idée repas.",
                    color = Colors.white,
                    style = Typography.subtitleBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.height(50.dp),
                    onClick = { params.action() },
                    colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = Colors.ternary,
                        contentColor = Colors.white
                    ),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(text = "Je découvre les recettes")
                }
            }
        }
    }
}

