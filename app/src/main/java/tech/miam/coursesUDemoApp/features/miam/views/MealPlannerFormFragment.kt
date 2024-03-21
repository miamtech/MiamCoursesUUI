package tech.miam.coursesUDemoApp.features.miam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ai.mealz.sdk.components.mealPlanner.form.MealPlannerForm
import tech.miam.coursesUDemoApp.R

class MealPlannerFormFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_planner_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mealPlannerForm = view.findViewById<MealPlannerForm>(R.id.meal_planner_form)
        mealPlannerForm.bind {
            findNavController().navigate(R.id.action_mealPlannerFormFragment_to_mealPlannerMealsFragment)
        }
    }
}