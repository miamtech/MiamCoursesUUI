package tech.miam.coursesUDemoApp.features.miam.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.miam.sdk.components.mealPlanner.basketPreview.MealPlannerBasketPreview
import tech.miam.coursesUDemoApp.R

class MealPlannerBasketPreviewFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_planner_basket_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mealPlannerBasketPreview = view.findViewById<MealPlannerBasketPreview>(R.id.meal_planner_basket_preview)
        mealPlannerBasketPreview.onComfirmBasket = {findNavController().navigate(R.id.action_mealPlannerBasketPreview_to_mealPlannerRecapFragment)}
        mealPlannerBasketPreview.onEmptyState = {findNavController().navigate(R.id.action_mealPlannerBasketPreview_to_mealPlannerFormFragment)}
    }
}
