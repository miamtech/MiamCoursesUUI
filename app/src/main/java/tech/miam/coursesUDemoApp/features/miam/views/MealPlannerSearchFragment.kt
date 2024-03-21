package tech.miam.coursesUDemoApp.features.miam.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ai.mealz.sdk.components.mealPlanner.replaceRecipePage.MealPlannerReplaceRecipePage
import tech.miam.coursesUDemoApp.R

class MealPlannerSearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.getString("amount")
        return inflater.inflate(R.layout.fragment_meal_planner_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index =  arguments?.getInt("index")
        val mealPlannerReplaceRecipe = view.findViewById<MealPlannerReplaceRecipePage>(R.id.meal_planner_replace_recipe)
        mealPlannerReplaceRecipe.bind { findNavController().navigate(R.id.action_mealPlannerSearchFragment_to_mealPlannerMealsFragment) }
        mealPlannerReplaceRecipe.index =  index ?: 0
    }
}