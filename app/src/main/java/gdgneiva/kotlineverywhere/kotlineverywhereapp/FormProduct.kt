package gdgneiva.kotlineverywhere.kotlineverywhereapp

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_form_product.*

class FormProduct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)
        setSupportActionBar(toolbar)

        val id = intent.getStringExtra("id")

        Toast.makeText(applicationContext, "ID PRODUCTO -> " + id, Toast.LENGTH_SHORT).show()

    }

}
