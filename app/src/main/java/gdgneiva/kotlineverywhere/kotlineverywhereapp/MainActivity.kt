package gdgneiva.kotlineverywhere.kotlineverywhereapp

import adapters.AdapterReciclerViewQuotation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import models.Product

class MainActivity : AppCompatActivity() {

    lateinit var rcvList: RecyclerView
    lateinit var btnNewProduct: FloatingActionButton

    val products: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            // Consulta solo el item seleccionado
            getProductById(product.id);
        }

        getProducts();


        // Click al boton de crear producto
        btnNewProduct.setOnClickListener() {
            val intent = Intent(this, FormProduct::class.java)
            startActivity(intent)
        }
    }

    fun getProducts() {

        products.clear()

        products.add(
            Product(
                1,
                "01",
                R.drawable.cocacola,
                "COCA-COLA",
                1500
            )
        );
        products.add(
            Product(
                2,
                "02",
                R.drawable.cocacola,
                "COCA-COLA LIGHT",
                2500
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );
        products.add(
            Product(
                3,
                "03",
                R.drawable.cocacola,
                "PAN ROLLO",
                1000
            )
        );

        rcvList.adapter?.notifyDataSetChanged();

    }

    fun getProductById(id: Int) {
        Toast.makeText(applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
    }


}