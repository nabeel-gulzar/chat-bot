package com.example.sohaibrabbani.psychx

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {

            R.id.settings -> {
                val intent= Intent(this,SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.help -> {

                return true
            }
            R.id.user_profile -> {
                val intent= Intent(this,EditProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        card_chatbot.setOnClickListener {
            val intent= Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
        card_settings.setOnClickListener {
            val intent= Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
        card_user_profile.setOnClickListener {
            val intent= Intent(this,EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

}
