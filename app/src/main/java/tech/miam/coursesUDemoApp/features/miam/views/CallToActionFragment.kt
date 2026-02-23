package tech.miam.coursesUDemoApp.features.miam.views

import ai.mealz.sdk.components.mealPlanner.callToAction.MealPlannerCallToAction
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ai.mealz.sdk.components.recipeJourney.RecipeJourney
import ai.mealz.uikit.ui.webview.FeatureRoute
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import tech.miam.coursesUDemoApp.R

class CallToActionFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_call_to_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val callToAction = view.findViewById<MealPlannerCallToAction>(R.id.CallToActionU)
        callToAction.bind { route ->
            findNavController().navigate(R.id.planner, bundleOf("route" to FeatureRoute.serialize(route)))
        }
        val recipe = view.findViewById<RecipeJourney>(R.id.Recipe)
        recipe.bind(recipeId = "15434")
    }
}