package tech.miam.coursesUDemoApp.features.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import tech.miam.coursesUDemoApp.R

interface BaseNavigationFragmentInterface {
    /**
     * Return if the fragment is a root fragment
     * Useful if we want to do some action according if it's a root fragment or not
     */
    val isRootFragment: Boolean

    /**
     * The id of the tab in which the fragment has to be displayed
     */
    val tabIdRes: Int

    /**
     * Optional, this method will be triggered EACH TIME for each [Fragment] implementing it, when the activity fires
     * the method [androidx.appcompat.app.AppCompatActivity.onBackPressed]
     */
    fun onBackPressed()

    /**
     * Triggered when user reselect the same current tab fragment
     */
    fun onTabReselected() {}

    /**
     * Allow to set a deeplink and refresh data if needed
     * @param intent the deep link intent to use
     */
    fun setDeepLinkIntent(intent: Intent?) {}
}

abstract class BaseNavigationFragment : Fragment(), BaseNavigationFragmentInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkTagName(toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set for every fragment the background color, rather to set it in each xml layout file
        view.setBackgroundResource(R.color.background_primary)
    }

    /**
     * Implement it here to make it optional for all [Fragment] inheriting of [BaseNavigationFragment]
     */
    override fun onBackPressed() {}

    /**
     * Check the given tag with the current class name.
     * Useful to ensure that when calling [toString] of the current [Fragment], it will return the same value as the
     * static TAG property declared in the companion object of the [Fragment] inheriting of [BaseNavigationFragment]
     */
    private fun checkTagName(tag: String): String {
        if (tag != this::class.java.simpleName) {
            throw IllegalArgumentException("Fragment class name ${this::class.java.simpleName} is different than expected $tag")
        }

        return tag
    }
}