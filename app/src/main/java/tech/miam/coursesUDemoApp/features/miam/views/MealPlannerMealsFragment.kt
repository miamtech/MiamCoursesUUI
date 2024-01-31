package tech.miam.coursesUDemoApp.features.miam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.miam.sdk.components.mealPlanner.meals.MealPlanner
import tech.miam.coursesUDemoApp.R

class MealPlannerMealsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_planner_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mealPlannerMeals = view.findViewById<MealPlanner>(R.id.meal_planner)
        mealPlannerMeals.goToRecipeSelector = { index -> findNavController().navigate(R.id.action_mealPlannerMealsFragment_to_mealPlannerSearchFragment, args = bundleOf("index" to index)) }
        mealPlannerMeals.onComfirm = { findNavController().navigate(R.id.action_mealPlannerMealsFragment_to_mealPlannerBasketPreview) }
       // TODO check mealPlannerMeals.onEmptyState = { findNavController().navigateUp() }
    }
}
