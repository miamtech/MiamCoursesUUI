package tech.miam.coursesUDemoApp.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.annotation.ColorInt
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

fun Drawable?.resize(size: Int): Drawable? = resize(size, size)

fun Drawable?.resize(width: Int, height: Int): Drawable? {
    if(this == null) return null
    val scale = intrinsicHeight.toDouble() / intrinsicWidth.toDouble()
    setBounds(0, 0, width, height)
    val boundsToApply = bounds
    if (boundsToApply.right != 0 || boundsToApply.bottom != 0) {
        if (boundsToApply.right == 0) {
            boundsToApply.right = (boundsToApply.bottom / scale).toInt()
            bounds = boundsToApply
        }
        if (boundsToApply.bottom == 0) {
            boundsToApply.bottom = (boundsToApply.right * scale).toInt()
            bounds = boundsToApply
        }
    }

    return this
}

/**
 * Apply a color to the drawable without impacting the drawable used somewhere else thanks to mutate
 */
fun Drawable.setTintColor(@ColorInt color: Int) = apply {
    mutate()
    colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
}

inline fun shapeDrawable(draw: GradientDrawable.() -> Unit): GradientDrawable = GradientDrawable().also {
    it.gradientType = GradientDrawable.LINEAR_GRADIENT
    it.draw()
}

inline fun ovalShapeDrawable(draw: GradientDrawable.() -> Unit): GradientDrawable = GradientDrawable().also {
    it.shape = GradientDrawable.OVAL
    it.draw()
}

inline fun rectangleShapeDrawable(draw: GradientDrawable.() -> Unit): GradientDrawable = GradientDrawable().also {
    it.shape = GradientDrawable.RECTANGLE
    it.draw()
}

inline fun GradientDrawable.stroke(draw: Stroke.() -> Unit): Stroke = Stroke().also {
    it.draw()
    setStroke(it.width, it.color, it.dashWidth, it.dashGap)
}

inline fun GradientDrawable.corners(draw: Corners.() -> Unit): Corners = Corners().also {
    it.draw()
    cornerRadii = it.render()
}

fun Corners.render(): FloatArray =
    floatArrayOf(topLeft.orRadius(), topLeft.orRadius(), topRight.orRadius(), topRight.orRadius(), bottomRight.orRadius(), bottomRight.orRadius(), bottomLeft.orRadius(), bottomLeft.orRadius())

var GradientDrawable.solidColor: Int
    set(value) = setColor(value)
    @Deprecated(message = NO_GETTER, level = DeprecationLevel.HIDDEN) get() = error(NO_GETTER)

var StateListDrawable.exitFadeDuration: Int
    set(value) = setExitFadeDuration(value)
    @Deprecated(message = NO_GETTER, level = DeprecationLevel.ERROR) get() = error(NO_GETTER)

class Stroke {
    var width: Int = -1
    var color: Int = -1
    var dashWidth: Float = 0f
    var dashGap: Float = 0f
}

class Corners {
    var radius: Float = 0f

    var topLeft: Float = Float.NaN
    var topRight: Float = Float.NaN
    var bottomLeft: Float = Float.NaN
    var bottomRight: Float = Float.NaN

    internal fun Float.orRadius(): Float = takeIf { it >= 0 } ?: radius
}

internal const val NO_GETTER = "Getter not available"