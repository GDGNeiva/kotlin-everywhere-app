package helps

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log

class SocialNetworks (val context: Context){

    private fun openUrl(url: String) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        context.startActivity(openURL)
    }

    fun openFb(url: String) {
        var uri = Uri.parse(url)
        val packageManager = context.packageManager
        try {
            val applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
            Log.e("SocialNetworks-Error", "Ocurrio un error")
        }
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    fun openGithub(url: String) {
        openUrl(url)
    }

    fun openTwitter(url: String) {
        openUrl(url)
    }

    fun openMeetup(url: String) {
        openUrl(url)
    }
}