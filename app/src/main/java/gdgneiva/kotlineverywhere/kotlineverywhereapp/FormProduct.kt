package gdgneiva.kotlineverywhere.kotlineverywhereapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import models.DBProduct
import models.Product

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
//import android.support.v7.app.AppCompatActivity
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import helps.Image
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class FormProduct : AppCompatActivity() {
    private lateinit var txtCode: EditText
    private lateinit var txtName: EditText
    private lateinit var txtPrice: EditText

    private lateinit var nameImgProduct: String

    private lateinit var objDBProduct: DBProduct
    private lateinit var objImage: Image

    private var btn: Button? = null
    private lateinit var imgProduct: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)

        // Obtenemos las referencias a los componentes de la vista
        imgProduct = findViewById(R.id.imgProduct)
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
        objImage = Image(applicationContext)

        // Cargamos los datos del productor a modificar, si existe
        loadProduct(id)

        // Agregamos los eventos a los componentes de la vista
        btnSave.setOnClickListener {
            var success = false
            if(id > 0) success = updateProduct(id) else success = insertProduct()

            // Finalizamos la activity para regresar a la principal
            if (success) {
                Toast.makeText(this, "Se guardo la informacion", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error, al guardar la informacion", Toast.LENGTH_LONG).show()
            }
        }

        imgProduct.setOnClickListener {takePhotoFromCamera()}
//        btn!!.setOnClickListener { takePhotoFromCamera() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun takePhotoFromCamera() {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                //permission already granted
                pickImageFromGallery();
            }
        } else{
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            try
            {
                val contentURI = data!!.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                val nameImg= objImage.save(bitmap)
                if (nameImg != "") {
                    nameImgProduct = nameImg
                    imgProduct.setImageBitmap(bitmap)
                    Toast.makeText(this, "Imagen guardada!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
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
            imgProduct.setImageURI(objImage.getUri(product.img))
            nameImgProduct = product.img
        }
    }

    private fun insertProduct(): Boolean {
        val objProduct = Product(
            0,
            txtCode.text.toString(),
            nameImgProduct,
            txtName.text.toString(),
            Integer.parseInt(txtPrice.getText().toString())
        )
        return objDBProduct.insert(objProduct);
    }

    private fun updateProduct(id: Int): Boolean {
        val objProduct = Product(
            id,
            txtCode.text.toString(),
            nameImgProduct,
            txtName.text.toString(),
            Integer.parseInt(txtPrice.text.toString())
        )
        return objDBProduct.update(objProduct);
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }
}
