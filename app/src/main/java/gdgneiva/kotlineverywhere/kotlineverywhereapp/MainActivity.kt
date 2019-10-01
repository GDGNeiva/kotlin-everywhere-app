package gdgneiva.kotlineverywhere.kotlineverywhereapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gdgneiva.kotlineverywhere.kotlineverywhereapp.DB.*
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactsViewModel = run {
            ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        }

        val phone = "3114554354"
        val name = "Oscar"
        val lastName = "Rodriguez"
        contactsViewModel.saveContact(Contact(phone, name, lastName))
    }
}
