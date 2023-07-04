package tech.miam.coursesUDemoApp.utils

import android.util.SparseArray
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.util.containsKey
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import tech.miam.coursesUDemoApp.R
import tech.miam.coursesUDemoApp.ui.component.BadgeView

class BadgeManager(private val bottomNavigationView: BottomNavigationView, private val animateBadges: Boolean = true) {

    companion object {
        // default duration animation
        private const val BADGE_ANIMATION_DURATION = 300L
    }

    /** Initialize the badge [SparseArray] according to the amount of [BottomNavigationView] menu items */
    private val badgeSparseArray: SparseArray<BadgeView> by lazy {
        SparseArray<BadgeView>(bottomNavigationView.menu.size())
    }

    /**
     * Show/create a badge
     * @param itemId the [BottomNavigationView] item id to target to put the badge
     * @param value the badge value
     * @param badgeBgColorRes an optional badge background color
     */
    fun showBadge(@IdRes itemId: Int, value: String, @ColorRes badgeBgColorRes: Int? = null) {
        if (!badgeSparseArray.containsKey(itemId)) {
            val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId) ?: return
            val badgeNotificationsView = LayoutInflater.from(bottomNavigationView.context).inflate(R.layout.view_badge_bottom_navigation_view, bottomNavigationView, false)
            badgeNotificationsView?.let { bnv ->
                bnv.findViewById<BadgeView>(R.id.view_badge_bottom_nav_view_text_tv)?.let {
                    if(badgeBgColorRes != null) {
                        it.tintBackgroundRes(badgeBgColorRes)
                    }
                    badgeSparseArray[itemId] = it
                    //badgeSparseArray[itemId]?.background = gd
                    itemView.addView(bnv)
                    if (animateBadges) {
                        it.scaleX = 0f
                        it.scaleY = 0f
                        it.animateBounce(duration = BADGE_ANIMATION_DURATION)
                    }
                }
            }
        }

        badgeSparseArray[itemId]?.text = value
    }

    /**
     * Remove a badge
     * @param itemId the [BottomNavigationView] item id to target to remove the badge
     */
    fun removeBadge(@IdRes itemId: Int) {
        val itemView = bottomNavigationView.findViewById<BottomNavigationItemView>(itemId)
        itemView?.let {
            if (it.childCount == 3) {
                it.removeViewAt(2)
                badgeSparseArray.remove(itemId)
            }
        }
    }
}