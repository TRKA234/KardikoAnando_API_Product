package mi2a.kardikoanando.api_product_kardikoanando.Service

import mi2a.kardikoanando.api_product_kardikoanando.Model.ResponseProduct
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("products")//end point
    fun getAllProduct(): Call<ResponseProduct>
}