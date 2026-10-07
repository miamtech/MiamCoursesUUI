package tech.miam.coursesUDemoApp.features.miam.views

import ai.mealz.uikit.ui.planner.PlannerNavigationCallback
import androidx.fragment.app.Fragment
import ai.mealz.uikit.ui.planner.PlannerScreen
import ai.mealz.uikit.ui.webview.FeatureRoute
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import tech.miam.coursesUDemoApp.R

class PlannerFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_planner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val planner = view.findViewById<PlannerScreen>(R.id.plannerView)
        val route = arguments?.getString("route")?.let {
            FeatureRoute.deserialize(it) as? FeatureRoute.Planner
        } ?: FeatureRoute.Planner.Dashboard()


        planner?.bind(
            route = route,
            navigationCallback = PlannerNavigationCallback(
                redirectToLogIn = {},
                redirectToStoreLocator = {},
                redirectToCart = { findNavController().popBackStack() },
                exit = { findNavController().popBackStack() },
                goToCatalog = { findNavController().navigate(R.id.catalogFragment) }
            )
        )
    }
}