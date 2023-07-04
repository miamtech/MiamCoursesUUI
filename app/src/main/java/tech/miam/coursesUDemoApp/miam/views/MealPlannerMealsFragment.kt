package tech.miam.coursesUDemoApp.miam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.miam.sdk.components.budget.callToAction.BudgetCallToAction
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

    }
}