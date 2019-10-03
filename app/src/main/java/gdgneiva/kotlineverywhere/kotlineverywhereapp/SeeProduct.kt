package gdgneiva.kotlineverywhere.kotlineverywhereapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import helps.Image

import models.DBProduct

class SeeProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_product)

        // Obtenemos los parametros enviados desde la actividad principal
        var id: Int = 0
        if (! intent.getStringExtra("id").isNullOrEmpty()) {
            id = Integer.parseInt(intent.getStringExtra("id"))

            val objDBProduct = DBProduct(applicationContext)
            val product = objDBProduct.selectById(id)

            Log.e("name", product.name)
            // Cargar los datos a la vista
            findViewById<TextView>(R.id.lblCode).text = product.code
            findViewById<TextView>(R.id.lblName).text = product.name
            findViewById<TextView>(R.id.lblPrice).text = product.price.toString()
            findViewById<ImageView>(R.id.imgProduct).setImageURI(Image(applicationContext).getUri(product.img))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
