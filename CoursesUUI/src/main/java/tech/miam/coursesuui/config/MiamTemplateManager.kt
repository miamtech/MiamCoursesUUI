package tech.miam.coursesuui.config

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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.miam.kmm_miam_sdk.android.theme.Colors
import com.miam.kmm_miam_sdk.android.theme.Colors.primary
import com.miam.kmm_miam_sdk.android.theme.Colors.white
import com.miam.kmm_miam_sdk.android.theme.Template
import com.miam.kmm_miam_sdk.android.theme.Typography
import com.miam.kmm_miam_sdk.android.ui.components.catalog.customization.CatalogImage
import com.miam.kmm_miam_sdk.android.ui.components.catalog.customization.CatalogText
import com.miam.sdk.templatesConfigs.MiamTheme
import com.miam.sdk.templatesConfigs.MiamTheme.mealPlanner
import tech.miam.coursesuui.component.CoursesUMealPlannerFooter
import tech.miam.coursesuui.template.mealPlanner.basketPreview.CoursesUBasketPreviewProductImp
import tech.miam.coursesuui.template.mealPlanner.basketPreview.MealPlannerBasketPreviewSectionTitleU
import tech.miam.coursesuui.template.mealPlanner.basketPreview.RecipeCardOverview
import tech.miam.coursesuui.template.mealPlanner.callToAction.MealPlannerCallToActionU
import tech.miam.coursesuui.template.mealPlanner.form.CoursesUBudgetForm
import tech.miam.coursesuui.template.mealPlanner.planner.CoursesUBudgetPlannerToolbar
import tech.miam.coursesuui.template.mealPlanner.recap.MealPlannerRecapU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.MealPlannerRecipeCardU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.MealPlannerRecipePlaceholderU
import tech.miam.coursesuui.template.mealPlanner.recipeCard.RecipeLoadingViewU
import tech.miam.coursesuui.template.mealPlanner.replaceRecipePage.MealPlannerReplaceRecipeSearchU
import tech.miam.coursesuui.template.mealPlanner.replaceRecipePage.MealPlannerSearchEmptyU


class MiamTemplateManager {
    init {
        MiamTheme.Template {
            mealPlanner {
                plannerFooterConfig {
                    view = CoursesUMealPlannerFooter()
                }
                plannerToolbarConfig {
                    view = CoursesUBudgetPlannerToolbar()
                }
                searchConfig {
                    view = MealPlannerReplaceRecipeSearchU()
                }
                callToActionConfig {
                    view = MealPlannerCallToActionU()
                }
                basketPreviewProductConfig {
                    view = CoursesUBasketPreviewProductImp()
                }
                basketPreviewFooterConfig {
                    view = CoursesUMealPlannerFooter()
                }
                basketPreviewSectionTitleConfig {
                    view = MealPlannerBasketPreviewSectionTitleU()
                }
                basketPreviewRecipeOverviewConfig {
                    view = RecipeCardOverview()
                }
                formConfig {
                    view = CoursesUBudgetForm()
                }
                recapConfig {
                    view = MealPlannerRecapU()
                }
                recipeCardConfig {
                    view = MealPlannerRecipeCardU()
                    recipePlaceholderConfig {
                        view = MealPlannerRecipePlaceholderU()
                    }
                    recipeLoadingConfig {
                        view = RecipeLoadingViewU()
                    }
                }
                searchEmpty {
                    view = MealPlannerSearchEmptyU()
                }
            }
        }

        Template.myMealEmptyTemplate = @Composable()
        { goToCatalog: () -> Unit ->
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
                        painter = painterResource(CatalogImage.empty),
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
                        onClick = { goToCatalog()},
                        colors =
                        ButtonDefaults.buttonColors(backgroundColor = Colors.ternary, contentColor = Colors.white),
                        shape = RoundedCornerShape(50),
                    ) {
                        Text(text = "Je découvre les recettes")
                    }
                }
            }
        }
    }
}