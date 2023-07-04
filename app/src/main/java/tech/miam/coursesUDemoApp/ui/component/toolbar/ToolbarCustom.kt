package tech.miam.coursesUDemoApp.ui.component.toolbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.get
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import tech.miam.coursesUDemoApp.R
import tech.miam.coursesUDemoApp.utils.getColorCompat
import tech.miam.coursesUDemoApp.utils.getDimens
import tech.miam.coursesUDemoApp.utils.setMarginFor
import tech.miam.coursesUDemoApp.utils.setTintColor

class ToolbarCustom : FrameLayout{
    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    companion object {
        const val ANIMATION_DURATION = 150
    }

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTextView: TextView
    private lateinit var toolbarTextSeparator: View
    private var toolbarTitleColor = -1

    private var isAnimatingShowing: Boolean = false
    private var isAnimatingHiding: Boolean = false
    private var animationDuration: Long = 0

    private var toolbarTitle: CharSequence = ""

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        getContext().obtainStyledAttributes(attrs, R.styleable.ToolbarCustom, defStyle, 0).apply {
            toolbarTitleColor = getResourceId(R.styleable.ToolbarCustom_tst_text_color, -1)

            animationDuration = getInt(R.styleable.ToolbarCustom_tst_duration, ANIMATION_DURATION).toLong()
            if (hasValue(R.styleable.ToolbarCustom_tst_text)) {
                toolbarTitle = getString(R.styleable.ToolbarCustom_tst_text) ?: ""
            }
            recycle()
        }

        inflate(context, R.layout.view_toolbar_custom, this)

        toolbar = findViewById(R.id.view_toolbar)
        var color = Color.TRANSPARENT
        val background = this.background
        if (background is ColorDrawable) color = background.color
        toolbar.setBackgroundColor(color)
        toolbarTextView = findViewById(R.id.view_toolbar_title_tv)
        toolbarTextSeparator = findViewById(R.id.view_toolbar_separator_view)

        // hide by default the title
        toolbarTextView.visibility = View.INVISIBLE
        if(toolbarTitleColor != -1) {
            setTitleColor(ContextCompat.getColor(context, toolbarTitleColor))
        }

        if(isInEditMode) {
            toolbarTextView.visibility = View.VISIBLE
            toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        }

        // tint the menu icons
        tintMenuIcons(toolbar.menu, R.color.foreground_primary)
        toolbar.overflowIcon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            ContextCompat.getColor(context, toolbarTitleColor), BlendModeCompat.SRC_ATOP)

        if(toolbarTitle.isNotEmpty()) {
            setTitle(toolbarTitle)
        }
    }

    /**
     * Set the toolbar title
     * @param title the toolbar to show and hide
     */
    fun setTitle(title: CharSequence) = apply {
        toolbarTitle = title
        toolbarTextView.text = toolbarTitle
        toolbarTextView.visibility = View.VISIBLE

        updateConstraints()
    }

    /**
     * Set the toolbar title
     * @param titleRes the toolbar to show and hide
     */
    fun setTitle(@StringRes titleRes: Int) = apply {
        setTitle(context.getString(titleRes))
    }

    /**
     * Set the menu into the toolbar
     * @param menuId the menu id to inflate into the toolbar
     * @param onMenuItemClickAction the lambda to handle clicks on menu items
     */
    fun menu(@MenuRes menuId: Int, onMenuItemClickAction: ((MenuItem) -> Unit)? = null) = apply {
        with(toolbar) {
            menu.clear()
            inflateMenu(menuId)
            setOnMenuItemClickListener { menuItem ->
                onMenuItemClickAction?.invoke(menuItem)
                true
            }

            tintMenuIcons(menu, R.color.foreground_primary)
        }

        updateConstraints()
    }

    /**
     * Clear the toolbar menu
     */
    fun clearMenu() = apply {
        toolbar.menu.clear()
    }

    /**
     * Add a menu item in the toolbar
     *
     * @param itemId the item id of the item to add to identify it and do action on click listener
     * @param name the item name
     * @param icon the item icon
     * @param order the item order
     * @param enabled set if the item must be enabled or disabled after added
     * @param onMenuItemClickAction the action to execute when clicking on this item
     */
    fun addMenuItem(itemId: Int, @StringRes name: Int, @DrawableRes icon: Int, showAsAction:Int = -1, order: Int = Menu.NONE,
                    enabled: Boolean = true, onMenuItemClickAction: ((MenuItem) -> Unit)? = null) = apply {
        addMenuItem(itemId, context.getString(name), icon, showAsAction, order, enabled, onMenuItemClickAction)
    }

    /**
     * Add a menu item in the toolbar
     *
     * @param itemId the item id of the item to add to identify it and do action on click listener
     * @param name the item name
     * @param icon the item icon
     * @param order the item order
     * @param enabled set if the item must be enabled or disabled after added
     * @param onMenuItemClickAction the action to execute when clicking on this item
     */
    fun addMenuItem(itemId: Int, name: String, @DrawableRes icon: Int, showAsAction:Int = -1, order: Int = Menu.NONE,
                    enabled: Boolean = true, onMenuItemClickAction: ((MenuItem) -> Unit)? = null) = apply {
        with(toolbar) {
            //menu.clear()
            val menuSize = getNbMenuItems()
            menu.add(Menu.NONE, itemId, order, name)
                .setIcon(icon).setEnabled(enabled)
                .setOnMenuItemClickListener {
                    onMenuItemClickAction?.invoke(it)
                    true
                }
                .setShowAsAction(if(showAsAction != -1) showAsAction else if(menuSize == 0) MenuItem.SHOW_AS_ACTION_IF_ROOM else MenuItem.SHOW_AS_ACTION_NEVER)

            tintMenuIcons(menu, R.color.foreground_primary)
        }

        updateConstraints()
    }

    /**
     * Return the menu of the toolbar
     */
    fun getMenu(): Menu = toolbar.menu

    /**
     * Return the number of items within menu
     */
    fun getNbMenuItems() = toolbar.menu?.size() ?: 0

    /**
     * Set the navigation up icon and an optional action
     * @param navigationUpIcon the icon of the navigation up
     * @param onUpIndicatorAction the lambda to handle click on the up navigation icon
     */
    fun showNavigationUp(@DrawableRes navigationUpIcon: Int? = null, onUpIndicatorAction: (() -> Unit)? = null) =
        apply {
            with(toolbar) {
                navigationIcon = navigationUpIcon?.let {
                    ContextCompat.getDrawable(context, it)
                } ?: ContextCompat.getDrawable(context, R.drawable.ic_back_arrow)

                onUpIndicatorAction?.let { action ->
                    setNavigationOnClickListener { action.invoke() }
                }
            }

            updateConstraints()
        }

    fun showNavigationUp(navigationUpIcon: Drawable? = null, onUpIndicatorAction: (() -> Unit)? = null) =
        apply {
            with(toolbar) {
                navigationIcon = navigationUpIcon ?: ContextCompat.getDrawable(context, R.drawable.ic_back_arrow)
                onUpIndicatorAction?.let { action ->
                    setNavigationOnClickListener { action.invoke() }
                }
            }

            updateConstraints()
        }

    /**
     * Show the title with animation
     */
    fun showTitle() {
        if (!isAnimatingShowing) {
            ViewCompat.animate(toolbarTextView).setDuration(animationDuration).withStartAction {
                isAnimatingHiding = false
                isAnimatingShowing = true
                toolbarTextView.visibility = View.VISIBLE
            }.y((this.height / 2).toFloat() - (toolbarTextView.height / 2).toFloat()).start()
        }
    }

    /**
     * Hide the title with animation
     */
    fun hideTitle() {
        if (!isAnimatingHiding) {
            ViewCompat.animate(toolbarTextView).setDuration(animationDuration).withStartAction {
                isAnimatingShowing = false
                isAnimatingHiding = true
            }.y(this.height.toFloat()).start()
        }
    }

    /**
     * Update constraints margins according to the populated menu
     */
    private fun updateConstraints() {
        val nbMenuItems = getNbMenuItems()
        val marginLeft = if(toolbar.navigationIcon != null) context.getDimens(R.dimen.toolbar_custom_text_margin_left).toInt() else context.getDimens(R.dimen.spacing_regular).toInt()
        var marginRight = context.getDimens(R.dimen.toolbar_custom_text_margin_left).toInt()

        if(nbMenuItems > 0) {
            // if we have a menu, then we're looking for number of expanded items to set good margins
            var nbItemsExpanded = 0
            for (i in 0 until nbMenuItems) {
                if(toolbar.menu[i].isActionViewExpanded) {
                    nbItemsExpanded++
                }
            }

            if(nbItemsExpanded > 0) {
                marginRight = nbMenuItems * context.getDimens(R.dimen.toolbar_custom_text_margin_right).toInt()
            }
        }

        toolbarTextView.setMarginFor(left = marginLeft, top = marginTop, right= marginRight, bottom = marginBottom)
    }

    /**
     * Tint all item within menu
     *
     * @param menu the menu in which the items will be tinted
     * @param color the color to apply on menu items
     */
    fun tintMenuIcons(menu: Menu?, @ColorRes color: Int) {
        menu?.let {
            val tintColor = context.getColorCompat(color)
            for (i in 0 until menu.size()) {
                val item = menu.getItem(i)
                tintMenuItemIcon(item, tintColor)
            }
        }
    }

    fun tintMenuIcons(@ColorRes color: Int) {
        tintMenuIcons(toolbar.menu, color)
    }

    /**
     * Tint a specific item within menu
     *
     * @param item the specific item which will be tinted
     * @param color the color to apply on menu item
     */
    fun tintMenuItemIcon(item: MenuItem, color: Int) {
        val drawable = item.icon
        if (drawable != null) {
            val wrapped = DrawableCompat.wrap(drawable)
            drawable.mutate()
            DrawableCompat.setTint(wrapped, color)
            item.icon = drawable
        }
    }

    fun setBackgroundColor(color:Int, inverseForegroundColor:Boolean) = apply {
        with(toolbar) {
            toolbar.setBackgroundColor(color)
            if(inverseForegroundColor) {
                tintMenuIcons(menu, R.color.foreground_primary_inverse)
                navigationIcon?.setTintColor(ContextCompat.getColor(context, R.color.foreground_primary_inverse))
                setTitleColor(ContextCompat.getColor(context, R.color.foreground_primary_inverse))
            } else {
                tintMenuIcons(menu, R.color.foreground_primary)
                navigationIcon?.setTintColor(ContextCompat.getColor(context, R.color.foreground_primary))
                setTitleColor(ContextCompat.getColor(context, R.color.foreground_primary))
            }
        }
    }

    fun setTitleColor(color:Int) = apply {
        toolbarTextView.setTextColor(color)
    }
}