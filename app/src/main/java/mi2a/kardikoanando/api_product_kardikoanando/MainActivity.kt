package mi2a.kardikoanando.api_product_kardikoanando

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mi2a.kardikoanando.api_product_kardikoanando.Adapter.ProductAdapter
import mi2a.kardikoanando.api_product_kardikoanando.Model.ModelProduct
import mi2a.kardikoanando.api_product_kardikoanando.Model.ResponseProduct
import mi2a.kardikoanando.api_product_kardikoanando.Service.ApiClient
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var recycleview  : RecyclerView
    private lateinit var call : Call<ResponseProduct>
    private lateinit var produkAdapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        recycleview = findViewById(R.id.rv_product)

        produkAdapter = ProductAdapter{modelProduk : ModelProduct -> produkOnClick(modelProduk) }
        recycleview.adapter = produkAdapter

        recycleview.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,
            false)

        swipeRefreshLayout.setOnRefreshListener {
            getData() //metho untuk proses ambil data
        }

        getData()


    }

    private fun produkOnClick(modelProduk: ModelProduct) {
        Toast.makeText(applicationContext, modelProduk.description,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getData() {
        swipeRefreshLayout.isRefreshing = true
        call = ApiClient.produkService.getAllProduct()
        call.enqueue(object : Callback<ResponseProduct> {
            override fun onResponse(
                call: Call<ResponseProduct>,
                response: Response<ResponseProduct>
            ) {
                swipeRefreshLayout.isRefreshing = false
                if(response.isSuccessful){
                    produkAdapter.submitList(response.body()?.products)
                    produkAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseProduct>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(applicationContext, t.localizedMessage,
                    Toast.LENGTH_SHORT).show()
            }
        })
    }
}