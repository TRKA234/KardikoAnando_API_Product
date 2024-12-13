package mi2a.kardikoanando.api_product_kardikoanando.Model

import android.os.Parcelable

data class ModelProduct(
    val id : Int,
    val title : String,
    val description : String,
    val category: String,
    val price : Double,
    val brand : String,
    val stock : Int,
    val thumbnail : String
)
