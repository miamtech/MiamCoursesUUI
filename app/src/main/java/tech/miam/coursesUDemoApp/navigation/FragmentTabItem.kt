package tech.miam.coursesUDemoApp.navigation

import androidx.annotation.DrawableRes
import tech.miam.coursesUDemoApp.features.navigation.BaseNavigationFragment

class FragmentTabItem(
    /** the tab name to display */
    val fragmentTabName:String,
    /** the tab icon to display */
    @DrawableRes val fragmentTabIcon: Int,
    /** the fragment name which is assigned to this tab in order to instantiate/show it */
    val fragment: BaseNavigationFragment
)