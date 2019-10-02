package gdgneiva.kotlineverywhere.kotlineverywhereapp

import adapters.AdapterReciclerViewQuotation
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import models.DBProduct
import models.Product



class MainActivity : AppCompatActivity() {
    private lateinit var objDBProduct: DBProduct

    lateinit var rcvList: RecyclerView
    lateinit var btnNewProduct: FloatingActionButton

    val products: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        objDBProduct = DBProduct(applicationContext)

        // configurar RecyclerView
        rcvList = findViewById<RecyclerView>(R.id.rcvList);
        rcvList.layoutManager = LinearLayoutManager(this)
        val adapterRecyclerView = AdapterReciclerViewQuotation(products, this);
        rcvList.adapter = adapterRecyclerView;
        rcvList.setHasFixedSize(true)

        // referencia al float button
        btnNewProduct = findViewById<FloatingActionButton>(R.id.btnNewProduct);

        // Click item del listado
        adapterRecyclerView.onItemClick = { product ->
            getProductById(product.id);
        }

        // Click item al boton ver producto
        adapterRecyclerView.onViewClick = { product ->
            Toast.makeText(applicationContext, "VER", Toast.LENGTH_SHORT).show()
        }

        // Click item al boton editar producto
        adapterRecyclerView.onEditClick = { product ->
            val intent = Intent(this, FormProduct::class.java)
            intent.putExtra("id", product.id.toString())
            startActivityForResult(intent, SUCCESS)
        }

        // Click item al boton eliminar producto
        adapterRecyclerView.onDeleteClick = { product ->
            if(objDBProduct.delete(product.id)){
                Toast.makeText(applicationContext, "Se elimino el producto " + product.name, Toast.LENGTH_SHORT).show()
                getProducts()
            }

        }

        getProducts();

        // Click al boton de crear producto
        btnNewProduct.setOnClickListener() {
            val intent = Intent(this, FormProduct::class.java)
            intent.putExtra("id", "")
            startActivityForResult(intent, SUCCESS)
        }
    }

    fun getProducts() {
        products.clear()
        products.addAll(objDBProduct.select())
        rcvList.adapter?.notifyDataSetChanged();
    }

    fun getProductById(id: Int) {
        Toast.makeText(applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getProducts()
    }

    companion object {
        const val SUCCESS = 1  // The request code
    }


}