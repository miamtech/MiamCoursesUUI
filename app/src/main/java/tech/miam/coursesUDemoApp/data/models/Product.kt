package tech.miam.coursesUDemoApp.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductWrapper(val data: Product): Parcelable

@Parcelize
data class Product(
    val id: String,
    val attributes: Attributes,
    var quantity: Int = 0
): Parcelable {
    val priceWithQuantity: Double
        get() {
            if (quantity == 0) {
                return attributes.price
            }
            return attributes.price * quantity
        }

    val identifier: String
        get() = "id_$id"

    override fun toString(): String {
        return "Name -> ${attributes.name} | Price -> $priceWithQuantity | Quantity -> $quantity"
    }
}

@Parcelize
data class Attributes(
    val name: String,
    val image: String,
    @SerializedName("unit-price")
    val price: Double,
    @SerializedName("ext-id")
    val extId: String,
    val description: String? = ""
): Parcelable