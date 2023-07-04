package tech.miam.coursesUDemoApp.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import tech.miam.coursesUDemoApp.R
import tech.miam.coursesUDemoApp.utils.getColorCompat
import tech.miam.coursesUDemoApp.utils.shapeDrawable
import tech.miam.coursesUDemoApp.utils.solidColor
import tech.miam.coursesUDemoApp.utils.stroke

class BadgeView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private var badgeBgColor = -1
    private var badgeRadius = 0f
    private var badgeStrokeColor = -1
    private var badgeStrokeWidth = 0
    private var badgeClickAnimation: Boolean = false
    private var badgeCheckable: Boolean = false
    private var badgeChecked: Boolean = false
    private var badgeTextAppearance: Int = -1

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        getContext().obtainStyledAttributes(attrs, R.styleable.BadgeView, defStyleAttr, 0)
            .apply {
                badgeBgColor = getResourceId(R.styleable.BadgeView_badge_bgColor, -1)
                badgeCheckable = getBoolean(R.styleable.BadgeView_badge_checkable, false)
                badgeChecked = getBoolean(R.styleable.BadgeView_badge_checked, false)
                badgeClickAnimation = getBoolean(R.styleable.BadgeView_badge_clickAnimation, false)
                badgeRadius = getDimensionPixelSize(R.styleable.BadgeView_badge_radius, 0).toFloat()
                badgeStrokeColor = getResourceId(R.styleable.BadgeView_badge_strokeColor, -1)
                badgeStrokeWidth = getDimensionPixelSize(R.styleable.BadgeView_badge_strokeWidth, 0)
                badgeTextAppearance = getResourceId(R.styleable.BadgeView_badge_textAppearance, -1)
                recycle()
            }

        background = shapeDrawable {
            solidColor = context.getColorCompat(badgeBgColor)
            cornerRadius = badgeRadius
            if (badgeStrokeWidth > 0) {
                stroke {
                    this.width = badgeStrokeWidth
                    this.color = context.getColorCompat(badgeStrokeColor)
                }
            }
        }

        gravity = Gravity.CENTER

        if (!isInEditMode) {
            // set the text appearance
            if (badgeTextAppearance != -1) {
                TextViewCompat.setTextAppearance(this, badgeTextAppearance)
            }
        }
    }
}