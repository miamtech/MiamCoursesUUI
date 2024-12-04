package tech.miam.coursesUDemoApp.features.miam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ai.mealz.sdk.components.mealPlanner.callToAction.MealPlannerCallToAction
import ai.mealz.sdk.components.recipeJourney.RecipeJourney
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
        val recipe = view.findViewById<RecipeJourney>(R.id.Recipe)
        callToAction.bind { findNavController().navigate(R.id.action_callToActionFragment_to_mealPlannerFormFragment) }
        recipe.bind(recipeId = "15434")
    }
}