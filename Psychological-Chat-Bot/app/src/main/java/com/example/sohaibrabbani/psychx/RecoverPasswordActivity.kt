package com.example.sohaibrabbani.psychx

import Model.DatabaseFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_recover_password.*

class RecoverPasswordActivity : AppCompatActivity() {

    lateinit var email: String
    lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        email = intent.getStringExtra("email")
    }


    fun resetPassword(view: View) {
        password = edit_text_new_password.text.toString()
        var confirmPass = edit_text_new_password_confirm.text.toString()

        if (Is_Valid_Password(password, confirmPass)) {
            if (::email.isInitialized && ::password.isInitialized) {
                PsychxWebService().resetPassword(this, email, DatabaseFilter.encryptPassword(password))
                recover_password_layout.isEnabled = false
            }
        } else {
            val errorSnackbar = Snackbar.make(findViewById(android.R.id.content), "invalid password", Snackbar.LENGTH_LONG)
            errorSnackbar.show()

        }
    }

    fun proceedToSignInActivity(status: Boolean) {
        if (status) {
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
            finish()
        } else {
            val errorSnackbar = Snackbar.make(findViewById(android.R.id.content), "There was an error resetting your password.", Snackbar.LENGTH_LONG)
            errorSnackbar.show()
        }
    }

    fun Is_Valid_Password(pass: String, confirm_pass: String): Boolean {

        if (pass.equals(confirm_pass) && pass.length >= 8)
            return true
        return false

    }
}
