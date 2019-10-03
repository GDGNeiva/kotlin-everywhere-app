package gdgneiva.kotlineverywhere.kotlineverywhereapp

import adapters.AdapterReciclerViewQuotation
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import models.DBProduct
import models.Product
import android.view.Menu
import android.net.Uri
import android.view.MenuItem
import android.content.pm.PackageManager

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
            val intent = Intent(this, SeeProduct::class.java)
            intent.putExtra("id", product.id.toString())
            startActivityForResult(intent, SUCCESS)
        }

        // Click item al boton ver producto
//        adapterRecyclerView.onViewClick = { product ->
//            Toast.makeText(applicationContext, "VER", Toast.LENGTH_SHORT).show()
//        }

        // Click item al boton editar producto
        adapterRecyclerView.onEditClick = { product ->
            val intent = Intent(this, FormProduct::class.java)
            intent.putExtra("id", product.id.toString())
            startActivityForResult(intent, SUCCESS)
        }

        // Click item al boton eliminar producto
        adapterRecyclerView.onDeleteClick = { product ->
            deleteProduct(product)
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

    fun deleteProduct(product: Product) {

        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@MainActivity)
        // Adicionar el titulo y descripcion
        builder.setTitle("Eliminar Producto")
        builder.setMessage("Esta seguro de eliminar el producto: ${product.name}?")

        // Adicionar los botones
        builder.setPositiveButton("Aceptar"){dialog, which ->
            // Eliminar el producto
            if(objDBProduct.delete(product.id)){
                Toast.makeText(applicationContext, "Se elimino el producto " + product.name, Toast.LENGTH_SHORT).show()
                getProducts()
            }
        }
        builder.setNegativeButton("Cancelar"){dialog,which ->
            Toast.makeText(applicationContext, "Accion cancelada!!", Toast.LENGTH_SHORT).show()
        }

        // Crear y abrir la ventana de confirmacion
        builder.create().show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.actFb-> {
                startActivity(newFacebookIntent(applicationContext, "https://www.facebook.com/GDGNeiva"))
                return true
            }
            R.id.actMeetup -> {
                val openURL = Intent(android.content.Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://www.meetup.com/es-ES/GDGNeiva/events/263280688/")
                startActivity(openURL)
                return true
            }
            R.id.actAbout -> {
                startActivityForResult(Intent(this, About::class.java), SUCCESS)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun newFacebookIntent(context: Context, url: String): Intent {
        var uri = Uri.parse(url)
        val packageManager = context.packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {

        }
        return Intent(Intent.ACTION_VIEW, uri)
    }

    companion object {
        const val SUCCESS = 1  // The request code
    }


}