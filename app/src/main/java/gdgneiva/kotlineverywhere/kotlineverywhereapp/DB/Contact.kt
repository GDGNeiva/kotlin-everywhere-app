package gdgneiva.kotlineverywhere.kotlineverywhereapp.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = Contact.TABLE_NAME)
data class Contact(
    @ColumnInfo(name = "phone_number") @NotNull val phoneNumber: String,
    @ColumnInfo(name = "first_name") @NotNull val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String? = null
) {
    companion object {
        const val TABLE_NAME = "contact"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    var contactId: Int = 0
}