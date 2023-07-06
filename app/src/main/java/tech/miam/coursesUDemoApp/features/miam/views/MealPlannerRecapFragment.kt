package tech.miam.coursesUDemoApp.features.miam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miam.sdk.components.mealPlanner.recap.MealPlannerRecap
import tech.miam.coursesUDemoApp.R

class MealPlannerRecapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_planner_recap, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealPlannerRecap = view.findViewById<MealPlannerRecap>(R.id.meal_planner_recap)
        mealPlannerRecap.action = { }
    }
}