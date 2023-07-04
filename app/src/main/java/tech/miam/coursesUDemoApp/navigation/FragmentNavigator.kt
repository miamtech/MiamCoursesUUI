package tech.miam.coursesUDemoApp.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import tech.miam.coursesUDemoApp.R
import tech.miam.coursesUDemoApp.features.navigation.BaseNavigationFragment
import timber.log.Timber
import java.util.ArrayList
import java.util.Collections
import java.util.LinkedHashMap
import java.util.Stack

class FragmentNavigator {
    private var stacks: MutableMap<Int, Stack<BaseNavigationFragment>>? = null
    private var stackList: MutableList<Int>? = null
    private var menuStacks: MutableList<Int>? = null
    private var currentFragment: BaseNavigationFragment? = null
    private var currentTab: Int = -1
    private var initialized = false
    private var rootFragments: MutableList<BaseNavigationFragment> = mutableListOf()

    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentManager: FragmentManager
    private var fragmentContainerid: Int = -1

    private fun FragmentTransaction.animate(): FragmentTransaction {
        return setCustomAnimations(
            pushFragmentAnimation[0], pushFragmentAnimation[1], pushFragmentAnimation[2],
            pushFragmentAnimation[3]
        )
    }

    private fun FragmentTransaction.animateBack(): FragmentTransaction {
        return setCustomAnimations(
            popFragmentAnimation[0], popFragmentAnimation[1], popFragmentAnimation[2],
            popFragmentAnimation[3]
        )
    }

    val isInitialized: Boolean
        get() = initialized

    fun reset() {
        fragmentManager.fragments.forEachIndexed { _, fragment ->
            Timber.tag("FragmentNavigator").d("🔫 destroying fragment $fragment")
            fragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss()
        }
        initialized = false
        stacks = null
        stackList = null
        menuStacks = null
        currentFragment = null
        currentTab = -1
        rootFragments = mutableListOf()
    }

    fun populateFragmentsRoot(fragmentsRoot: MutableList<FragmentTabItem>) {
        this.fragmentsRoot = fragmentsRoot
        this.fragmentsRoot.forEach {
            rootFragments.add(it.fragment)
        }
    }

    fun initWith(bottomNavView: BottomNavigationView, fm: FragmentManager, fragmentContainerid: Int, defaultTabId: Int) {
        bottomNavigationView = bottomNavView
        fragmentManager = fm
        this.fragmentContainerid = fragmentContainerid

        bottomNavigationView.setOnNavigationItemSelectedListener {
            try {
                selectedTab(it.itemId)
            } catch (e: Exception) {
                return@setOnNavigationItemSelectedListener false
            }
            return@setOnNavigationItemSelectedListener true
        }

        bottomNavigationView.setOnNavigationItemReselectedListener {
            try {
                if (currentTab != it.itemId) {
                    selectedTab(it.itemId)
                } else {
                    // trigger action when user reselect the same current tab
                    // the onTabReselected() is a method from interface to listen in fragments
                    getCurrentFragmentFromShownStack().onTabReselected()
                }
            } catch (e: Exception) {

            }
        }

        stacks = LinkedHashMap()
        stackList = ArrayList()
        menuStacks = ArrayList()

        bottomNavigationView.menu.forEach { menuItem ->
            stacks?.let { it[menuItem.itemId] = Stack() }
            stackList?.add(menuItem.itemId)
        }

        bottomNavigationView.selectedItemId = defaultTabId

        initialized = true
    }

    private fun getFragmentRoot(fragmentTagName: String): BaseNavigationFragment? {
        if (rootFragments.isNotEmpty()) {
            rootFragments.forEach {
                if (it.toString() == fragmentTagName) {
                    return it
                }
            }
        }

        return null
    }

    private lateinit var fragmentsRoot: MutableList<FragmentTabItem>

    /**
     * Look which fragment is assigned to the tab id
     */
    fun getFragmentFromTabId(tabId: Int): FragmentTabItem? {
        fragmentsRoot.forEach {
            if (it.fragment.tabIdRes == tabId) {
                return it
            }
        }

        return null
    }

    fun getTabIdFromFragmentName(fragmentName: String): Int {
        fragmentsRoot.forEach {
            if (it.fragment.toString() == fragmentName) {
                return it.fragment.tabIdRes
            }
        }

        throw IllegalAccessException("Cannot find the tab id for $fragmentName")
    }

    fun selectedTab(tabId: Int, fragmentDeepLinkIntent: Intent? = null) {
        currentTab = tabId

        bottomNavigationView.menu.findItem(tabId).isChecked = true // force to highlight the according tab

        if (stacks!![tabId]?.size == 0) {
            /*
             * First time this tab is selected. So add first fragment of that tab.
             * We are adding a new fragment which is not present in stack. So add to stack is true.
             */
            val fragmentNameFromTabId: FragmentTabItem? = getFragmentFromTabId(tabId)
            val fragment: BaseNavigationFragment = getFragmentRoot(fragmentNameFromTabId!!.fragment.toString())!!
            if (currentFragment == null) {
                addInitialTabFragment(
                    fragmentManager, stacks!!, tabId, fragment,
                    fragmentContainerid, true
                )
            } else {
                addAdditionalTabFragment(
                    fragmentManager, stacks!!, tabId, fragment, currentFragment!!,
                    fragmentContainerid, true
                )
            }
            resolveStackLists(tabId)
            assignCurrentFragment(fragment)
            currentFragment?.setDeepLinkIntent(fragmentDeepLinkIntent)
        } else {
            /*
             * We are switching tabs, and target tab already has at least one fragment.
             * Show the target fragment
             */
            showHideTabFragment(fragmentManager, stacks!![tabId]!!.lastElement(), currentFragment!!)
            resolveStackLists(tabId)
            assignCurrentFragment(stacks!![tabId]!!.lastElement())
            if(fragmentDeepLinkIntent != null) {
                currentFragment?.setDeepLinkIntent(fragmentDeepLinkIntent)
            }
        }
    }

    private fun popFragment() {
        /*
         * Select the second last fragment in current tab's stack,
         * which will be shown after the fragment transaction given below
         */
        val fragment = stacks!![currentTab]!!.elementAt(stacks!![currentTab]!!.size - 2)

        /*pop current fragment from stack */
        stacks!![currentTab]!!.pop()

        removeFragment(fragmentManager, fragment, currentFragment!!)

        assignCurrentFragment(fragment)
    }

    /**
     * Handle back stack when pressing back button or up button from toolbar
     * @param activity the activity to finish if there is no more elements in the stack to show
     */
    fun resolveBackPressed(activity: AppCompatActivity) {
        if (currentTab == -1) {
            // then there something wrong here, then finish the activity
            activity.finishAndRemoveTask()
            return
        }

        var stackValue = 0
        if (stacks != null && stacks!![currentTab]!!.size == 1) {
            val value = stacks!![stackList?.get(1)]
            if (value != null && value.size > 1) {
                stackValue = value.size
                popAndNavigateToPreviousMenu()
            }
            if (stackValue <= 1) {
                if (menuStacks!!.size > 1) {
                    navigateToPreviousMenu()
                } else {
                    activity.finishAndRemoveTask()
                }
            }
        } else {
            popFragment()
        }
    }

    /*Pops the last fragment inside particular tab and goes to the second tab in the stack*/
    private fun popAndNavigateToPreviousMenu() {
        val tempCurrent = stackList!![0]
        currentTab = stackList!![1]
        bottomNavigationView.selectedItemId = resolveTabPositions(currentTab)
        showHideTabFragment(fragmentManager, stacks!![currentTab]!!.lastElement(), currentFragment!!)
        assignCurrentFragment(stacks!![currentTab]!!.lastElement())
        updateStackToIndexFirst(stackList, tempCurrent)
        menuStacks!!.removeAt(0)
    }

    private fun navigateToPreviousMenu() {
        menuStacks!!.removeAt(0)
        currentTab = menuStacks!![0]
        bottomNavigationView.selectedItemId = resolveTabPositions(currentTab)
        showHideTabFragment(fragmentManager, stacks!![currentTab]!!.lastElement(), currentFragment!!)
        assignCurrentFragment(stacks!![currentTab]!!.lastElement())
    }

    private fun resolveTabPositions(currentTab: Int): Int {
        var tabIndex = 0

        bottomNavigationView.menu.forEach {
            if (currentTab == it.itemId) {
                tabIndex = it.itemId
                return@forEach
            }
        }

        return tabIndex
    }

    fun resolveStackLists(tabId: Int) {
        updateStackIndex(stackList, tabId)
        updateTabStackIndex(menuStacks, tabId)
    }

    fun assignCurrentFragment(current: BaseNavigationFragment) {
        currentFragment = current
    }

    /**
     * Get the current fragment displayed from stack of the current selected tab
     */
    fun getCurrentFragmentFromShownStack(): BaseNavigationFragment =
        stacks!![currentTab]!!.elementAt(stacks!![currentTab]!!.size - 1)

    /**
     * Push a fragment
     * @param tabId the tab id where the fragment will be pushed in order to highlight the according tab
     * @param fragmentToAdd the fragment to push
     * @param addToBackStack true if must add the fragment transaction within back stack, otherwise false
     */
    fun pushFragment(tabId: Int, fragmentToAdd: BaseNavigationFragment, addToBackStack: Boolean = true) {
        bottomNavigationView.menu.findItem(tabId).isChecked = true // force to highlight the according tab
        addShowHideFragment(
            fragmentManager, stacks!!, tabId, fragmentToAdd, currentFragment,
            fragmentContainerid, addToBackStack
        )
        resolveTabPositions(tabId)
        assignCurrentFragment(fragmentToAdd)
    }

    /**
     * Add the initial fragment, in most cases the first tab in BottomNavigationView
     */
    private fun addInitialTabFragment(
        fragmentManager: FragmentManager, stacks: Map<Int, Stack<BaseNavigationFragment>>, tag: Int,
        fragment: BaseNavigationFragment, layoutId: Int, shouldAddToStack: Boolean
    ) {
        if (shouldAddToStack) stacks.getValue(tag).push(fragment)
        fragmentManager.beginTransaction().add(layoutId, fragment).commit()
    }

    /**
     * Add additional tab in BottomNavigationView on click, apart from the initial one and for the first time
     */
    private fun addAdditionalTabFragment(
        fragmentManager: FragmentManager, stacks: Map<Int, Stack<BaseNavigationFragment>>,
        tag: Int, show: BaseNavigationFragment, hide: BaseNavigationFragment, layoutId: Int,
        shouldAddToStack: Boolean
    ) {
        if (shouldAddToStack) stacks.getValue(tag).push(show)
        fragmentManager.beginTransaction().add(layoutId, show).show(show).hide(hide).commit()
    }

    /**
     * Hide previous and show current tab fragment if it has already been added
     * In most cases, when tab is clicked again, not for the first time
     */
    private fun showHideTabFragment(fragmentManager: FragmentManager, show: BaseNavigationFragment, hide: BaseNavigationFragment) {
        fragmentManager.beginTransaction().hide(hide).show(show).commit()
    }

    /**
     * Add fragment in the particular tab stack and show it, while hiding the one that was before
     */
    private fun addShowHideFragment(
        fragmentManager: FragmentManager, stacks: Map<Int, Stack<BaseNavigationFragment>>, tag: Int,
        show: BaseNavigationFragment, hide: BaseNavigationFragment?, layoutId: Int, shouldAddToStack: Boolean
    ) {
        if (shouldAddToStack) stacks.getValue(tag).push(show)
        val fragmentTransaction = fragmentManager.beginTransaction().animate().add(layoutId, show).show(show)

        if (hide != null) {
            fragmentTransaction.hide(hide)
        }

        fragmentTransaction.commit()
    }

    fun removeFragment(fragmentManager: FragmentManager, show: BaseNavigationFragment, remove: BaseNavigationFragment) {
        fragmentManager.beginTransaction().animateBack().remove(remove).show(show).commit()
    }

    /**
     * Just remove a fragment, without animation
     */
    private fun removeFragment(fragmentManager: FragmentManager, remove: BaseNavigationFragment) {
        fragmentManager.beginTransaction().remove(remove).commit()
    }

    private fun findFragmentFromStack(fragmentToFind: BaseNavigationFragment): BaseNavigationFragment? {
        stacks!![currentTab]?.forEach {
            if (it.toString() == fragmentToFind.toString()) {
                return it
            }
        }
        return null
    }

    companion object {
        const val NAVIGATE_TO_FRAGMENT = "navigate_to_fragment"

        /** fragment animation for push  */
        val pushFragmentAnimation: MutableList<Int> = mutableListOf(
            R.anim.slide_left_from_outside,
            R.anim.slide_left_to_outside,
            R.anim.slide_right_from_outside,
            R.anim.slide_right_to_outside
        )

        /** fragment animation for back pressed for root fragment (Bottom Bar Navigation)  */
        val popFragmentAnimation: MutableList<Int> = mutableListOf(
            R.anim.slide_right_from_outside,
            R.anim.slide_right_to_outside,
            R.anim.slide_left_from_outside,
            R.anim.slide_left_to_outside
        )

        /**
         * Keeps track of clicked tabs and their respective stacks
         * Swaps the tabs to first position as they're clicked
         * Ensures proper navigation when back presses occur
         */
        private fun updateStackIndex(list: MutableList<Int>?, tabId: Int) {
            if (list == null) return
            while (list.indexOf(tabId) != 0) {
                val i = list.indexOf(tabId)
                Collections.swap(list, i, i - 1)
            }
        }

        /**
         * Keeps track of when switching between tabs occur
         * The next tab to be shown is pushed on top
         * The tab which was current before is now pushed as last
         */
        private fun updateStackToIndexFirst(stackList: MutableList<Int>?, tabId: Int) {
            if (stackList == null) return
            val stackListSize = stackList.size
            var moveUp = 1
            while (stackList.indexOf(tabId) != stackListSize - 1) {
                val i = stackList.indexOf(tabId)
                Collections.swap(stackList, moveUp++, i)
            }
        }

        /**
         * Keeps track of the clicked tabs and ensures proper navigation if there are no nested fragments in the tabs
         * When navigating back, the user will end up on the first clicked tab
         * If the first tab is clicked again while navigating, the user will end up on the second tab clicked
         */
        private fun updateTabStackIndex(tabList: MutableList<Int>?, tabId: Int) {
            if (tabList == null) return
            if (!tabList.contains(tabId)) {
                tabList.add(tabId)
            }
            while (tabList.indexOf(tabId) != 0) {
                val i = tabList.indexOf(tabId)
                Collections.swap(tabList, i, i - 1)
            }
        }
    }
}