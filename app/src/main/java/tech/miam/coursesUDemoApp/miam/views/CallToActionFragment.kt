package tech.miam.coursesUDemoApp.miam.views

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.miam.sdk.components.budget.callToAction.BudgetCallToAction
import tech.miam.coursesUDemoApp.R
import tech.miam.coursesuui.template.mealPlanner.callToAction.CallToActionU

class CallToActionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_call_to_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val callToAction = view.findViewById<BudgetCallToAction>(R.id.CallToActionU)
        callToAction.bind { findNavController().navigate(R.id.action_callToActionFragment_to_mealPlannerFormFragment)  }
    }
}