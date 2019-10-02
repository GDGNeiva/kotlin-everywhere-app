package gdgneiva.kotlineverywhere.kotlineverywhereapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import models.DBProduct
import models.Product

class FormProduct : AppCompatActivity() {
    private lateinit var txtCode: EditText
    private lateinit var txtName: EditText
    private lateinit var txtPrice: EditText

    private lateinit var objDBProduct: DBProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)

        // Obtenemos las referencias a los componentes de la vista
        txtCode = findViewById(R.id.txtCode)
        txtName = findViewById(R.id.txtName)
        txtPrice = findViewById(R.id.txtPrice)
        val btnSave = findViewById(R.id.btnSave) as Button

        // Obtenemos los parametros enviados desde la actividad principal
        var id: Int = 0
        if (! intent.getStringExtra("id").isNullOrEmpty()) {
            id = Integer.parseInt(intent.getStringExtra("id"))
        }

        // Instanciamos la clase del modelo Product, encargada de la DB
        objDBProduct = DBProduct(applicationContext)

        // Cargamos los datos del productor a modificar, si existe
        loadProduct(id)

        // Agregamos los eventos a los componentes de la vista
        btnSave.setOnClickListener {
            if(id > 0) updateProduct(id) else insertProduct()
            // Finalizamos la activity para regresar a la principal
            finish()
        }
    }

    private fun loadProduct(id: Int){
        if (id > 0) {
            // Obtenemos los datos del producto que esta en la DB
            val product = objDBProduct.selectById(id)
            // Asignar los atributos del producto a los componentes de la view
            txtCode.setText(product.code)
            txtName.setText(product.name)
            txtPrice.setText(product.price.toString())
        }
    }

    private fun insertProduct() {
        val objProduct = Product(
            0,
            txtCode.text.toString(),
            R.drawable.cocacola,
            txtName.text.toString(),
            Integer.parseInt(txtPrice.getText().toString())
        )
        objDBProduct.insert(objProduct);
    }

    private fun updateProduct(id: Int) {
        val objProduct = Product(
            id,
            txtCode.text.toString(),
            R.drawable.cocacola,
            txtName.text.toString(),
            Integer.parseInt(txtPrice.text.toString())
        )
        val result: Boolean = objDBProduct.update(objProduct);
        if (result) {
            Toast.makeText(this, "Se actualizo correctamente", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error, al actualizar", Toast.LENGTH_LONG).show()
        }
    }
}
