package tech.miam.coursesUDemoApp.utils

import android.R
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.SystemClock
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

/**
 * Debounce click, preventing to make action several times by clicking like a monkey
 *
 * @param intervalBetweenClicks the interval to wait between clicks to be able to execute the action
 * @param action the action to execute on click
 */
inline fun View.setSafeOnClickListener(intervalBetweenClicks: Long = 500L, crossinline action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastTimeClicked: Long = 0
        override fun onClick(v: View?) {
            if (SystemClock.elapsedRealtime() - lastTimeClicked < intervalBetweenClicks) {
                return
            }
            lastTimeClicked = SystemClock.elapsedRealtime()
            action()
        }
    })
}

inline fun View.setSafeOnClickListener(crossinline action: () -> Unit) {
    setSafeOnClickListener(500, action)
}

/**
 * Apply a tint color to the background
 *
 * @param color the background color resource
 */
fun View.tintBackgroundRes(@ColorRes color: Int) =
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, color)))

/**
 * Apply a tint color to the background
 *
 * @param color the background [ColorStateList]
 */
fun View.tintBackgroundColorStateList(color: ColorStateList) = ViewCompat.setBackgroundTintList(this, color)

/**
 * Apply a tint color to the background
 *
 * @param color the background color
 */
fun View.tintBackground(color: Int) = ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))

/**
 * Set the view margin
 *
 * @param left   the margin left
 * @param top    the margin top
 * @param right  the margin right
 * @param bottom the margin bottom
 */
fun View.setMarginFor(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.setMargins(left ?: lp.leftMargin, top ?: lp.topMargin, right ?: lp.rightMargin, bottom ?: lp.bottomMargin)
    layoutParams = lp
}

fun View.setMargins(all: Int? = null) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    lp.setMargins(all ?: lp.leftMargin, all ?: lp.topMargin, all ?: lp.rightMargin, all ?: lp.bottomMargin)
    layoutParams = lp
}

/**
 * Apply padding only for given sides, sides which are not set keep their default current padding
 *
 * @param left   the padding left
 * @param top    the padding top
 * @param right  the padding right
 * @param bottom the padding bottom
 */
fun View.setPaddingFor(left: Int = this.paddingStart, top: Int = this.paddingTop, right: Int = this.paddingEnd,
                       bottom: Int = this.paddingBottom) = this.setPadding(left, top, right, bottom)

/**
 * Apply padding horizontal and vertical
 *
 * @param horizontal   the horizontal padding
 * @param vertical     the vertical padding
 */
fun View.setPaddingHV(horizontal: Int = 0, vertical: Int = 0) = this.setPadding(horizontal, vertical, horizontal, vertical)

/**
 * Apply same padding for all sides
 *
 * @param all   the padding to apply to all sides
 */
fun View.setPaddings(all: Int = 0) = this.setPadding(all, all, all, all)

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(gone: Boolean = false) {
    visibility = if (gone) {
        View.GONE
    } else {
        View.INVISIBLE
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun ViewGroup.getColorCompat(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this.context, colorRes)
fun View.getColorCompat(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this.context, colorRes)

@SuppressLint("ObjectAnimatorBinding")
fun View.animateTextColorInt(@ColorInt fromColorRes: Int, @ColorInt toColorRes: Int, duration: Long = 200L) {
    val colorAnim: ObjectAnimator = ObjectAnimator.ofInt(this, "textColor", fromColorRes, toColorRes)
    colorAnim.duration = duration
    colorAnim.setEvaluator(ArgbEvaluator())
    colorAnim.start()
}

const val DEFAULT_SCALE_UP_FACTOR = 1f
inline fun View.animateBounce(scaleX: Float = DEFAULT_SCALE_UP_FACTOR, scaleY: Float = DEFAULT_SCALE_UP_FACTOR, duration: Long = 200L, crossinline doOnEnd: () -> Unit = {}) {
    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", scaleX)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", scaleY)
    scaleDownX.duration = duration
    scaleDownY.duration = duration

    val scaleDown = AnimatorSet()
    scaleDown.play(scaleDownY).with(scaleDownX)
    scaleDown.interpolator = OvershootInterpolator()
    scaleDownY.doOnEnd {
        doOnEnd()
    }
    scaleDown.start()
}