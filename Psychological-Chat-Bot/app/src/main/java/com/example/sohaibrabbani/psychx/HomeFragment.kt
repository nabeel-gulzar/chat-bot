package com.example.sohaibrabbani.psychx

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("Dashboard")
        card_chatbot.setOnClickListener {
            startActivity(Intent(activity,ChatActivity::class.java))
        }
        card_settings.setOnClickListener {
            startActivity(Intent(activity,SettingsActivity::class.java))
        }
        card_user_profile.setOnClickListener {
            startActivity(Intent(activity,EditProfileActivity::class.java))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
