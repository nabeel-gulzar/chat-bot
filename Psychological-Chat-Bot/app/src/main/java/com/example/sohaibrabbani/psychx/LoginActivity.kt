package com.example.sohaibrabbani.psychx

//import kotlinx.android.synthetic.main.activity_sign_up.*
import Model.DatabaseFilter
import Model.User
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Telephony
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.sohaibrabbani.psychx.sync.BGService
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() , Animation.AnimationListener {


    var mContext = this
    var startA = true
    var aEnded = false
    lateinit var strEmail: String
    lateinit var strPass: String
    lateinit var input: TextInputEditText
    lateinit var suggested_emails: Array<String>
    var a=false
    private var mReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        suggested_emails = DatabaseFilter.getSharedPreferenceSuggestionEmails(this)

        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                val phone = msgs[0].originatingAddress
                val body = msgs[0].messageBody
                Log.d("Message", body)
                Log.d("Message", phone)
                if (phone == ("+923314184050") || phone == ("+923009658434")) {
                    if (::input.isInitialized)
                        input.setText(body)
                }
            }
        }
        //registering our receiver

//        val i = Intent(this, BackgroundService::class.java)
//        startService(i)
        val i = Intent(this, BGService::class.java)
        startService(i)
        val loggedIn = DatabaseFilter.isLoggedin(this)
        if(loggedIn)
        {
            val intent= Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }


        //region EditText Listener Email
        edt_text_email.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Is_Valid_Email(edt_text_email)
            }
        })
        //endregion

        //region EditText Listener Password
        edt_text_password.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                Is_Valid_Password(edt_text_password)
            }
        })
        //endregion

        //region Button Listener SignIn
        btn_sign_in.setOnClickListener {

            var email = edt_text_email.text.toString()
            var pass = edt_text_password.text.toString()
            if(edt_text_email.error != null || edt_text_password.error != null || edt_text_email.text.isEmpty() || edt_text_password.text.isEmpty())
            {
                Snackbar.make(findViewById(R.id.layout), "Invalid credentials", Snackbar.LENGTH_LONG).show()

                //region Hide Keyboard
                val view = this.currentFocus
                if (view != null) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                //endregion
            }
            else
            {
                DatabaseFilter.setSharedPreferenceSuggestionEmails(this, edt_text_email.text.toString())
                Log.d("Login", "Pressed")
                var webService = PsychxWebService()
                webService.getVerifiedUser(this, email, DatabaseFilter.encryptPassword(pass))
            }

        }
        //endregion

        //region Animation Code

        if(savedInstanceState != null)
        {
            startA=savedInstanceState.getBoolean("START_ANIMATION")
        }

        if(startA && intent.action == "android.intent.action.MAIN" && intent.categories.contains("android.intent.category.LAUNCHER"))
        {
            img_logo.visibility=View.GONE
            textView.visibility=View.GONE
            text_email.visibility=View.GONE
            txt_link_forgot_password.visibility=View.GONE
            text_password.visibility=View.GONE
            btn_sign_up.visibility=View.GONE
            btn_sign_up.visibility=View.GONE
            val moveFBLogoAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.move_logo)
            moveFBLogoAnimation.fillAfter = true
            moveFBLogoAnimation.setAnimationListener(this)
            img_logo_goner.startAnimation(moveFBLogoAnimation)
        }
        else
        {
            img_logo_goner.visibility=View.GONE
        }

        layout.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
            if (aEnded || !startA) {
                val heightDiff = layout.rootView.height - layout.height
                if (heightDiff > dpToPx(this, 200f)) {
                    //Soft keyboard is shown
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        img_logo.visibility=View.GONE
                        textView.visibility=View.GONE
                        text_email.visibility=View.GONE
                        txt_link_forgot_password.visibility=View.GONE
                        text_password.visibility=View.GONE
                        btn_sign_up.visibility=View.GONE
                        btn_sign_up.visibility=View.GONE
                    }
                } else {
                    //Soft keyboard is hidden
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        img_logo.visibility=View.VISIBLE
                        textView.visibility=View.VISIBLE
                        text_email.visibility=View.VISIBLE
                        txt_link_forgot_password.visibility=View.VISIBLE
                        text_password.visibility=View.VISIBLE
                        btn_sign_up.visibility=View.VISIBLE
                        btn_sign_up.visibility=View.VISIBLE

                    }

                }
            }
        })
        //endregion
    }

    //region Textview Listener Forgot Password
    fun forgotPasswordListener(view: View)
    {
        val viewInflated = layoutInflater.inflate(R.layout.dialog_recover_password, null)
        input = viewInflated.findViewById(R.id.edt_text_secret_code) as TextInputEditText



        if (edt_text_email.text.toString().isEmpty()) {
            val askEmailSnackbar = Snackbar.make(findViewById(R.id.layout), "Please enter your email.", Snackbar.LENGTH_LONG)
            askEmailSnackbar.show()
        } else {
            DatabaseFilter.Recovery_Phone = ""
            PsychxWebService().forgotPassword(edt_text_email.text.toString())
            var dialog = ProgressDialog(this)
                dialog.setMessage("Verifying Account, please wait.")
                dialog.show()
            while (DatabaseFilter.Recovery_Phone.isEmpty()) {
            }
            if(dialog.isShowing)
                dialog.dismiss()

            val recoveryTextBuilder = AlertDialog.Builder(this, R.style.MyAlertDialogTheme)
                    .setCancelable(false)
                    .setTitle("Please wait, your recovery code has been sent to phone number ending with " +
                            DatabaseFilter.Recovery_Phone.substring(DatabaseFilter.Recovery_Phone.length - 4, DatabaseFilter.Recovery_Phone.length - 1))
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("OK") { dialog, which ->
                        var response = DatabaseFilter.Recovery_Token.toString().trim()
                        var user_input = input.text.toString().trim()
                        if (user_input.equals(response)) {
                            val intent = Intent(this, RecoverPasswordActivity::class.java)
                            intent.putExtra("email", edt_text_email.text.toString())
                            startActivity(intent)
                        } else {
                            input.text.clear()
                        }
                    }
                    .setView(viewInflated)
            recoveryTextBuilder.create().show()

        }


//        val emailTextBuilder = AlertDialog.Builder(this,R.style.MyAlertDialogTheme)
//                .setCancelable(false)
//                .setTitle("Write Your Phone")
//                .setNegativeButton("Cancel"){dialog, which ->
//                    dialog.dismiss()
//                }
//                .setPositiveButton("OK"){dialog, which ->
//                    var email: String
//                    email = email_txt.text.toString()
//                    if(!email.isEmpty()) {
//                        PsychxWebService().forgotPassword(email)
//                        recoveryTextBuilder.create().show()
//                    }
//
//                }
//                .setView(viewInflated)
//        emailTextBuilder.create().show()

    }

    override fun onStart() {
        super.onStart()
        try {
            val intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
            registerReceiver(mReceiver, intentFilter)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggested_emails)
        edt_text_email.setAdapter(adapter)
        edt_text_email.threshold = 1
        var all_emails = DatabaseFilter.getSharedPreferenceSuggestionEmails(this)
        edt_text_email.setText(all_emails[all_emails.size - 1])

    }

    override fun onPause() {
        super.onPause()
        try {
            this.unregisterReceiver(this.mReceiver)
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
    //endregion

    //region Button Listener SignUp
    fun btnSignUpListener (view: View)
    {
        val intent= Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }
    //endregion

    //region Validation Email
    fun Is_Valid_Email(edt: EditText) {
        if (edt.text.toString().isEmpty()) {
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

    //region Validation Password
    fun Is_Valid_Password(edt: TextInputEditText) {
        if (edt.text.toString().length <= 7) {
            edt.error = "At least 8 characters."
            strPass = ""
        } else {
            strPass = edt.text.toString()

        }
    }
    //endregion

    //region Overrided methods
    override fun onRestart() {
        super.onRestart()

        val sharedPref = this.getSharedPreferences("PsychX", Context.MODE_PRIVATE)
        val loggedIn = sharedPref.getBoolean(getString(R.string.sp_logged_in), false)
        if(loggedIn)
        {
            finish()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean("START_ANIMATION", false)
    }
    //endregion

    //region Functions For Animation
    fun dpToPx(context: Context, valueInDp: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }

    override fun onAnimationRepeat(p0: Animation?) {
        val a=true
    }

    override fun onAnimationStart(p0: Animation?) {
        img_logo_goner.visibility=View.VISIBLE
    }

    override fun onAnimationEnd(p0: Animation?) {
        img_logo.visibility = View.VISIBLE


        img_logo.alpha=0f
        textView.alpha=0f
        text_password.alpha=0f
        text_email.alpha=0f
        txt_link_forgot_password.alpha=0f
        btn_sign_up.alpha=0f
        btn_sign_up.alpha=0f

        img_logo.visibility=View.VISIBLE
        textView.visibility=View.VISIBLE
        text_email.visibility=View.VISIBLE
        txt_link_forgot_password.visibility=View.VISIBLE
        text_password.visibility=View.VISIBLE
        btn_sign_up.visibility=View.VISIBLE
        btn_sign_up.visibility=View.VISIBLE


        val m = resources.getInteger(android.R.integer.config_mediumAnimTime)
        val mediumAnimationTime=m.toLong()
        img_logo.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)

        txt_link_forgot_password.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        text_email.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        btn_sign_up.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        text_password.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        btn_sign_up.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        textView.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        img_logo_goner.animate()
                .alpha(0f)
                .setDuration(mediumAnimationTime)
                .setListener(null)
        img_logo_goner.clearAnimation()
        img_logo_goner.visibility=View.GONE
        aEnded = true
    }
    //endregion

    fun ProceedToDashboard(user: User)
    {

        val userValidationSnackbar = Snackbar.make(findViewById(R.id.layout), "Invalid credentials", Snackbar.LENGTH_LONG)

        var service = PsychxWebService()



        if(user!=null)
        {
//            delete all records from hobbies and insert updated hobbies to db, and sp [Async]
            service.getAllHobbies(this)
//            delete all records from user hobbies and insert updated user hobbies db, and sp [Async]
            service.getAllUserHobbies(this, user.uid)
//            save logged in user to database, after deleting all other users [Async]
            DatabaseFilter.insertUser(this, user)
//            save logged in user data to shared preference, set logged in to true
            DatabaseFilter.setSharedPreference(this, user)


            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        else
        {
            val view = currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            userValidationSnackbar.setText("Invalid credentials")
            layout.isEnabled=true
        }
    }
}

