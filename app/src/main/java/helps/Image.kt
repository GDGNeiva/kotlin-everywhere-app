package helps

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import androidx.core.content.res.TypedArrayUtils
import gdgneiva.kotlineverywhere.kotlineverywhereapp.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class Image (val context: Context) {

    private var dirBase: String = context.getApplicationInfo().dataDir + context.getString(R.string.rs_img_product)

    fun save(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

        val wallpaperDirectory = File(dirBase)
        // Crear el directorio si no existe
        if (! wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try
        {
            val nameImg = (Calendar.getInstance().getTimeInMillis()).toString() + ".jpg"
            val f = File(wallpaperDirectory, nameImg)
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(context, arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
            fo.close()
//            return f.absolutePath
            return nameImg
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    fun getUri(name: String): Uri {
        return Uri.parse("$dirBase/$name")
    }
}