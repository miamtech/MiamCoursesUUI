package tech.miam.coursesUDemoApp.features.miam.views

import ai.mealz.sdk.components.catalogJourney.CatalogJourney
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.miam.coursesUDemoApp.R

class CatalogFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catalog = view.findViewById<CatalogJourney>(R.id.catalogView)

//        catalog?.bind(
//            goToBasket = {
//                findNavController().navigate(R.id.myMealsFragment)
//            }
//        )
    }
}