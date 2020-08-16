package com.example.sohaibrabbani.psychx

import Model.DatabaseFilter
import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*


class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        var u = DatabaseFilter.getSharedPreference(this)
        val view_nav = nav_view.getHeaderView(0)
        view_nav.findViewById<TextView>(R.id.nav_header_name).setText(u.firstName + " " + u.lastName)
        view_nav.findViewById<TextView>(R.id.nav_header_email).setText(u.email)
        //view_nav.findViewById<CircleImageView>(R.id.nav_header_profile_photo).background=u.image
        //u.image
        displayScreen(-1)


    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {

                DatabaseFilter.deleteAllUsers(this)
                DatabaseFilter.destroySession(this)

                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun displayScreen(itemId: Int)
    {
        val fragment = when (itemId) {
            R.id.nav_home-> {
                HomeFragment()
            }
            R.id.nav_chatbot -> {
                ChatActivity()
            }
            R.id.nav_user_profile -> {
                EditProfileActivity()
            }
            R.id.nav_emotion_all -> {
                ReportingAllEmotionFragment()
            }
            R.id.nav_daily_chart -> {
                ReportingDailyChartFragment()
            }
            R.id.nav_weekly_chart -> {
                ReportingWeeklyChartFragment()
            }
            R.id.nav_monthly_chart -> {
                ReportingMonthlyChartFragment()
            }
            R.id.nav_about -> {

            }
            R.id.nav_sign_out -> {
                DatabaseFilter.deleteAllUsers(this)
                DatabaseFilter.destroySession(this)

                finish()
            }
            else -> {
                HomeFragment()
            }
        }
        if(fragment is Fragment)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
        else if (fragment is Activity)
            startActivity(Intent(this,fragment::class.java))

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        displayScreen(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
