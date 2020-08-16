package com.example.sohaibrabbani.psychx

import Model.AppDatabase
import Model.DatabaseFilter
import Model.User
import Model.UserHobby
import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.view.animation.AlphaAnimation
import android.widget.*
import com.example.sohaibrabbani.psychx.provider.SyncAccount
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class EditProfileActivity : ArrayAdapterClickHelper, AppCompatActivity(),AppBarLayout.OnOffsetChangedListener  {
    //region Variables
    private var user: User= User()
    lateinit var mAppDatabase: AppDatabase

    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
    private val ALPHA_ANIMATIONS_DURATION = 200

    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true

    private var mTitleContainer: LinearLayout? = null
    private var mTitle: TextView? = null
    private var mAppBarLayout: AppBarLayout? = null
    private var mToolbar: Toolbar? = null

    lateinit var inputDb: ArrayList<String>
    lateinit var content: ArrayList<String>
    lateinit var selectedData: ArrayList<Int>
    lateinit var listview: ListView
//    lateinit var email: String

    val EXTRA_NAME = "cheese_name"
    private val REQUEST_IMAGE_CAPTURE = 1122
    private val REQUEST_IMAGE_LOAD = 2211

    lateinit var AUTHORITY_PROFILE: String
    lateinit var AUTHORITY_NEW: String

    lateinit var bitmap: Bitmap
    //    var photoChanged: Boolean = false
    lateinit var dob: String

    // An account type, in the form of a domain name
    lateinit var ACCOUNT_TYPE: String
    // The account name
    val ACCOUNT = "PsychX"
    // Instance fields
    lateinit var account: Account
    // Global variables
    // A content resolver for accessing the provider
    var mResolver: ContentResolver? = null
    //endregion

    //region UI Methods
    private fun bindActivity() {
        mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mTitle = findViewById<TextView>(R.id.main_textview_title)
        mTitleContainer = findViewById<LinearLayout>(R.id.main_linearlayout_title)
        mAppBarLayout = findViewById<AppBarLayout>(R.id.main_appbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_settings -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()

        handleAlphaOnTitle(percentage)
        handleToolbarTitleVisibility(percentage)
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                mIsTheTitleVisible = true
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                mIsTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
                mIsTheTitleContainerVisible = false
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
                mIsTheTitleContainerVisible = true
            }
        }
    }

    fun startAlphaAnimation(v: View?, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE)
            AlphaAnimation(0f, 1f)
        else
            AlphaAnimation(1f, 0f)

        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v!!.startAnimation(alphaAnimation)
    }
    //endregion

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedData= ArrayList() //integer array containing position of all newly selected hobbies

        AUTHORITY_PROFILE = getString(R.string.content_authority_profile)
        ACCOUNT_TYPE = getString(R.string.sync_account_type)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_edit_profile)


        btnUpdate.setOnClickListener{

            var int_gender = spinner_gender.selectedItemPosition
            val char_gender = when(int_gender)
            {
                0 -> 'm'
                1 -> 'f'
                else -> 'o'
            }
            var u = DatabaseFilter.getSharedPreference(this)
            u.valid=0
            u.phone=edit_text_phone.text.toString()
            u.gender= char_gender
            u.firstName=edit_text_display_name.text.toString().split(" ")[0].trim()
            u.lastName=edit_text_display_name.text.toString().removePrefix(u.firstName).trim()
            u.image = "profile_"+u.email+".png"
            u.dob = edit_text_age.text.toString()

            DatabaseFilter.updateUser(this, u)
            if(inputDb.size > 0)
                DatabaseFilter.insertUserHobbiesList(this, inputDb, u.uid)
            else
            {
                DatabaseFilter.deleteAllUserHobbies(this)
                var userHobbyList = ArrayList<UserHobby>()
//                PsychxWebService().insertUserHobbyList(userHobbyList)
            }



            DatabaseFilter.setSharedPreference(this, u)
            DatabaseFilter.setSharedPreference_UserHobbiesWithTitle(this, inputDb)

//            var service = PsychxWebService()

            var syncAcc: SyncAccount = SyncAccount(ACCOUNT_TYPE)
            syncAcc.CreateSyncAccount(this)
            syncAcc.RunSyncing(AUTHORITY_PROFILE,"Profile"/*,edittext_chatbox.text.toString(),formatter.format(Date()),DatabaseFilter.getSharedPreference(this).uid,"Chat",this*/)

//            service.getAllUserHobbies(this, u.uid)
            /*if(::bitmap.isInitialized) {
                Log.d("Bitmap", "YES")
                service.updateUser(u, bitmap)
            }
            else {
                Log.d("Bitmap", "NO")
                service.updateUser(u)
            }*/
            finish()
        }

        bindActivity()

        mAppBarLayout!!.addOnOffsetChangedListener(this)

        mToolbar!!.inflateMenu(R.menu.menu_main)
        startAlphaAnimation(mTitle, 0, View.INVISIBLE)

        loadBackdrop()
    }


    fun hobbyListener(view: View)
    {
        selectedData.clear()
        popupMultipleCheckBoxDialog(content, inputDb)
    }
    override fun onStart() {
        super.onStart()
        Log.d("Build Version", Build.VERSION.SDK_INT.toString() +":"+Build.VERSION_CODES.N.toString())
        user = DatabaseFilter.getSharedPreference(this)
        content = DatabaseFilter.getSharedPreference_Hobbies(this)
        inputDb= DatabaseFilter.getSharedPreference_UserHobbies(this)
        updateHobbyText(inputDb)
        /*        Glide.with(this)
                .load(user.image)
                .into(img_profile)

        val charset = Charsets.UTF_8
        var photoBytes = user.image.toByteArray(charset)

        var arrayInputStream = ByteArrayInputStream(photoBytes)
        var photoBitmap =  BitmapFactory.decodeStream(arrayInputStream)
        var byteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(user.image)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        img_profile.setImageBitmap(photoBitmap)*/
        //region Phone Number
        edit_text_phone.setText(user.phone)
        //endregion

        //region Gender
        if(user.gender.equals('m'))
            spinner_gender.setSelection(((spinner_gender.adapter) as ArrayAdapter<String>).getPosition("Male"))
        else if(user.gender.equals('f'))
            spinner_gender.setSelection(((spinner_gender.adapter) as ArrayAdapter<String>).getPosition("Female"))
        else if(user.gender.equals('o'))
            spinner_gender.setSelection(((spinner_gender.adapter) as ArrayAdapter<String>).getPosition("Other"))

        //endregion

        //region Display Name
        edit_text_display_name.setText(user.firstName+" "+user.lastName)
        //endregion

        //region Date of Birth
        dob = user.dob
        edit_text_age.setText(dob)
        //endregion

    }


    fun ageListener(view: View) {
        Log.d("Build Version", Build.VERSION.SDK_INT.toString() +":"+Build.VERSION_CODES.N.toString())
        Log.d("Build Version", "ha")

        val calendar = Calendar.getInstance()
        var day=calendar.get(Calendar.DAY_OF_MONTH)
        var month=calendar.get(Calendar.MONTH)
        var year=calendar.get(Calendar.YEAR)

        var datePickerDialog = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, null, year, month, day)

        Log.d("Build Version", Build.VERSION.SDK_INT.toString() +":"+Build.VERSION_CODES.N.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                var mMon: String = (month + 1).toString()
                var mDay: String = dayOfMonth.toString()
                if(dayOfMonth <10)
                    mDay="0"+dayOfMonth
                if (month<10) {
                    mMon="0"+(month + 1)
                }
                edit_text_age.setText(year.toString() + "-" + mMon + "-" + mDay)
                dob = year.toString() + "-" + mMon + "-" + mDay
            }
        }

        datePickerDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()

    }

    fun profileImageListener(view: View) {
        val builder = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val viewImage = factory.inflate(R.layout.layout_profile_picture, null)
        val imageView = viewImage.findViewById(R.id.dialog_imageview) as ImageView
        builder.setTitle("Pick photo")
                .setCancelable(false)
        builder.setView(viewImage)
                .setPositiveButton("Gallery") { dialog, id ->
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_LOAD)
                }
                .setNegativeButton("Camera") { dialog, id ->
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (takePictureIntent.resolveActivity(packageManager) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
        imageView.setImageDrawable(img_profile.drawable)
        builder.setCancelable(true)
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val extras = data.extras
                val imageBitmap = extras!!.get("data") as Bitmap
                img_profile.setImageBitmap(imageBitmap)
            }
        }
        else if (requestCode == REQUEST_IMAGE_LOAD && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                    img_profile.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
    }

    private fun loadBackdrop() {
        //Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).into(img_cover)

    }

    protected fun popupMultipleCheckBoxDialog(totalData: ArrayList<String>, userData: ArrayList<String>) {

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val builder = AlertDialog.Builder(this)

        val view = inflater.inflate(R.layout.layout_dialog_hobby, null)
        listview = view.findViewById(R.id.list_hobby) as ListView
        val adapter = MultipleCheckBoxAdapter(this,
                R.layout.layout_custom_list_item_hobby,totalData, userData, this)
        listview.adapter = adapter

        val dialog = builder
                .setView(view)
                .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id -> onPositiveButtonClick(selectedData) })
                .setNegativeButton("CANCEL",
                        DialogInterface.OnClickListener { dialog, id -> }).create()
        dialog.show()

    }

    override fun clickDelegate(position: Int, checked: Boolean) {
        if (checked) {
            selectedData.add(position)
            inputDb.add(content[position])
        } else {
            selectedData.remove(Integer.valueOf(position))
            inputDb.remove(content[position])
        }
    }

    override fun onPositiveButtonClick(selectedData: ArrayList<Int>) {
        txt_hobby.text=""
        updateHobbyText(inputDb)

    }

    private fun updateHobbyText(selectedData: ArrayList<String>)
    {
        txt_hobby.text = ""
        for(hobby in selectedData)
        {
            if (txt_hobby.text.isEmpty())
                txt_hobby.text = hobby
            else
                txt_hobby.text = txt_hobby.text.toString() + "\r\n" + hobby
        }
    }
}
