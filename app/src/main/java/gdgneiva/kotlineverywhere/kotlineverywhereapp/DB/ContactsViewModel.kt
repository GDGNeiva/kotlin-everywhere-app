package gdgneiva.kotlineverywhere.kotlineverywhereapp.DB

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ContactsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContactsRepository(application)
    val contacts = repository.getContacts()

    fun saveContact(contact: Contact) {
        repository.insert(contact)
    }
}