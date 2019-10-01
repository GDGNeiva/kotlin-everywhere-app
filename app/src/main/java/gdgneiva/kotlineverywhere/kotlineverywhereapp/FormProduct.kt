package gdgneiva.kotlineverywhere.kotlineverywhereapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import gdgneiva.kotlineverywhere.kotlineverywhereapp.DB.Contact
import gdgneiva.kotlineverywhere.kotlineverywhereapp.DB.ContactsViewModel

import kotlinx.android.synthetic.main.activity_form_product.*

class FormProduct : AppCompatActivity() {
    private lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_product)

        contactsViewModel = run {
            ViewModelProviders.of(this).get(ContactsViewModel::class.java)
        }
        val btn_click_me = findViewById(R.id.btnSave) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            addContact()
        }
        addObserver()
    }
//
    private fun addObserver() {
        val observer = Observer<List<Contact>> { contacts ->
            if (contacts != null) {
                var text = ""
                for (contact in contacts) {
                    text += contact.lastName + " " + contact.firstName + " - " + contact.phoneNumber + "\n"
                }
                contacts_textView.text = text
            }
        }
        contactsViewModel.contacts.observe(this, observer)
    }

    private fun addContact() {
        val phone = "3333"
        val name = "oscar"
        val lastName = "Rodriguez"
        contactsViewModel.saveContact(Contact(phone, name, lastName))
    }

}
