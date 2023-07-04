package tech.miam.coursesUDemoApp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.toast(msg: String, toastDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, toastDuration)
        .show()
}

fun Context.getColorCompat(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)
fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? = ContextCompat.getDrawable(this, drawableRes)

fun Context.getDimensPixel(@DimenRes dimension: Int): Int = resources.getDimensionPixelSize(dimension)
fun Context.getDimensPixelToFloat(@DimenRes dimension: Int): Float = resources.getDimensionPixelSize(dimension).toFloat()
fun Context.getDimens(@DimenRes dimension: Int): Float = resources.getDimension(dimension)
fun Context.getDimensToInt(@DimenRes dimension: Int): Int = resources.getDimension(dimension).toInt()