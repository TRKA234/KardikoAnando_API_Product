package mi2a.kardikoanando.api_product_kardikoanando.Adapter

import android.content.Intent
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mi2a.kardikoanando.api_product_kardikoanando.DetailProductActivity
import mi2a.kardikoanando.api_product_kardikoanando.Model.ModelProduct
import mi2a.kardikoanando.api_product_kardikoanando.R

class ProductAdapter(
    private val onClick: (ModelProduct) -> Unit
) : ListAdapter<ModelProduct, ProductAdapter.ProductViewHolder>(ProductCallBack) {


    class ProductViewHolder(itemView: View, val onClick: (ModelProduct) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val imgProduct : ImageView = itemView.findViewById(R.id.imgProduct)
        private val title : TextView = itemView.findViewById(R.id.txtTitle)
        private val brand : TextView = itemView.findViewById(R.id.txtBrand)
        private val price : TextView = itemView.findViewById(R.id.txtPrice)
        val cardProduct: View = itemView.findViewById(R.id.cardProduct)

        //cek product saat ini
        private var currentProduct:ModelProduct? = null

        init {
            itemView.setOnClickListener(){
                currentProduct?.let {
                    onClick(it)
                }
            }
        }


        fun bind(product: ModelProduct){
            currentProduct = product
            //set ke widget
            title.text = product.title
            brand.text = product.brand
            price.text = product.price.toString()

            //untuk menampilkan gambar
            Glide.with(itemView).load(product.thumbnail).centerCrop()
                .into(imgProduct)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_product_item,parent,false
        )
        return ProductViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        holder.cardProduct.setOnClickListener() {
            val intent = Intent(holder.itemView.context, DetailProductActivity::class.java)
            intent.putExtra("title", product.title)
            intent.putExtra("description", product.description)
            intent.putExtra("thumbnail", product.thumbnail)
            intent.putExtra("price", product.price)
            intent.putExtra("stok", product.stock)
            holder.itemView.context.startActivity(intent)
        }
    }

}

object ProductCallBack : DiffUtil.ItemCallback<ModelProduct>() {
    override fun areItemsTheSame(oldItem: ModelProduct, newItem: ModelProduct): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ModelProduct, newItem: ModelProduct): Boolean {
        return oldItem.id == newItem.id
    }

}
