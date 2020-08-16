package com.example.sohaibrabbani.psychx

import Model.AppDatabase
import Model.DatabaseFilter
import Model.User
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.io.ByteArrayOutputStream
import java.io.IOException


class SignUpActivity : AppCompatActivity() {

    //region Declarations
    var mContext=this
    val SELECT_IMAGE = 1000
    lateinit var mAppDatabase: AppDatabase
    lateinit var user: User
    lateinit var strName: String
    lateinit var strEmail: String
    lateinit var strPass: String
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //region Initialization
        user = User()
        strName= String()
        strEmail= String()
        strPass= String()
        //endregion

        //region EditText Listener Password
        edit_text_password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                Is_Valid_Password(edit_text_password,edit_text_password_confirm)
            }
        })

        edit_text_password_confirm.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                Is_Valid_Password(edit_text_password,edit_text_password_confirm)
            }
        })
        //endregion

        //region EditText Listener Name
        edit_text_name.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                Is_Valid_Person_Name(edit_text_name)
            }
        })
        //endregion

        //region EditText Listener Email
        edit_text_email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                Is_Valid_Email(edit_text_email)
            }
        })
        //endregion

        //region Clickable Text Login
        val spanStr = SpannableString(getString(R.string.login_here))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                finish()
            }
        }
        spanStr.setSpan(clickableSpan, spanStr.length-11, spanStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        txt_link_login.text = spanStr
        txt_link_login.movementMethod = LinkMovementMethod.getInstance()
        txt_link_login.highlightColor = Color.TRANSPARENT
        //endregion Text Login

    }

    //region Button Listener Sign Up
    fun btnSignUpListener(view: View){
        if (strName.isNotEmpty() && strEmail.isNotEmpty() && strPass.isNotEmpty()) {
            val name = edit_text_name.text.toString().split(" ")
            user.firstName = name[0]
            user.lastName = edit_text_name.text.toString().removePrefix(name[0])
            user.email = edit_text_email.text.toString()
            user.dob = "1990-01-01"
            user.phone = ""
            user.gender = 'o'
            if(edit_text_password != null)
                user.password = DatabaseFilter.encryptPassword(edit_text_password.text.toString())
            else
                return


            PsychxWebService().createUser(user)



        }
        else
        {
            Snackbar.make(view, "Invalid credentials", Snackbar.LENGTH_LONG).show()

            //region Hide Keyboard
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            //endregion
        }
        //replace it with the line below after web service is written and server is configured
        //            DatabaseFilter.signUpUser(user, edit_text_password_confirm.toString())
    }
    //endregion

    //region Validation Password
    fun Is_Valid_Password(edt: TextInputEditText,edtConfirm: TextInputEditText) {
        if (edt.text.toString().length <= 7) {
            edt.error = "At least 8 characters."
            strPass = ""
        }
        else if (edtConfirm.text.toString().length <= 7) {
            edtConfirm.error = "At least 8 characters."
            strPass = ""
        }else if (edt.text.toString() != edtConfirm.text.toString()) {
            edt.error = "Passwords are not same."
            edtConfirm.error = "Passwords are not same."
            strPass = ""
        } else {
            strPass = edt.text.toString()
            edt.error = null
            edtConfirm.error = null

        }
    }
    //endregion

    //region Button Listener Add Picture
    fun btnAddPictureListener(view: View)
    {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {

                        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                        //user.image=bitmapToByteArray(bitmap)
                        image_view_profile.setImageBitmap(bitmap)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //endregion

    //region Convert Bitmap to ByteArray
    fun bitmapToByteArray(b: Bitmap) :ByteArray{
        val opstream = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.PNG, 100, opstream)
        val bytArray = opstream.toByteArray()
        return bytArray
    }
    //endregion

    //region Validation Name
    @Throws(NumberFormatException::class)
    fun Is_Valid_Person_Name(edt: TextInputEditText) {
        if (edt.text.toString().length <= 0) {
            edt.error = "Accept Alphabets Only."
            strName=""
        } else if (!edt.text.toString().matches("[a-zA-Z ]+".toRegex())) {
            edt.error = "Accept Alphabets Only."
            strName=""
        } else {
            strName = edt.text.toString()
        }
    }
    //endregion

    //region Validation Email
    fun Is_Valid_Email(edt: EditText) {
        if (edt.text.toString() == null) {
            edt.error = "Invalid Email Address"
            strEmail = ""
        } else if (isEmailValid(edt.text.toString()) === false) {
            edt.error = "Invalid Email Address"
            strEmail = ""
        } else {
            strEmail = edt.text.toString()
        }
    }

    fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    //endregion




}

