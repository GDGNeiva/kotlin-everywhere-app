package gdgneiva.kotlineverywhere.kotlineverywhereapp

import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import helps.SocialNetworks

import kotlinx.android.synthetic.main.activity_about.*

class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btnFb = findViewById<ImageView>(R.id.btnFb)
        val btnGitHub= findViewById<ImageView>(R.id.btnGithub)
        val btnTwitter = findViewById<ImageView>(R.id.btnTwitter)
        val btnMeetup = findViewById<ImageView>(R.id.btnMeetup)

        btnFb.setOnClickListener() {
            SocialNetworks(applicationContext).openFb(getString(R.string.uri_fb))
        }

        btnGitHub.setOnClickListener() {
            SocialNetworks(applicationContext).openGithub(getString(R.string.uri_github))
        }

        btnTwitter.setOnClickListener() {
            SocialNetworks(applicationContext).openTwitter(getString(R.string.uri_twitter))
        }

        btnMeetup.setOnClickListener() {
            SocialNetworks(applicationContext).openMeetup(getString(R.string.uri_meetup))
        }
    }
}
