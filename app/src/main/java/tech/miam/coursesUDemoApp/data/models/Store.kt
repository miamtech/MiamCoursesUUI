package tech.miam.coursesUDemoApp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Store(
    val id: String,
    val name: String
): Parcelable {
    override fun toString(): String = "Store $id - $name"
}