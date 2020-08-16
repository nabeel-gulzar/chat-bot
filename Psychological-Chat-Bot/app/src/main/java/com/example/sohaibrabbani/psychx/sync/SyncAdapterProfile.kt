package com.example.sohaibrabbani.psychx.sync

import Model.ChatHistory
import Model.DatabaseFilter
import Model.UserHobby
import android.accounts.Account
import android.arch.persistence.room.Room
import android.content.*
import android.os.Bundle
import android.util.Log
import android.os.AsyncTask
import com.example.sohaibrabbani.psychx.ChatActivity
import com.example.sohaibrabbani.psychx.PsychxWebService
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


/**
 * Created by sohaibrabbani on 1/4/2018.
 */

public class SyncAdapterProfile : AbstractThreadedSyncAdapter {


    // Global variables
    // Define a variable to contain a content resolver instance
    lateinit var mContentResolver: ContentResolver

    constructor(context: Context, autoInitialize: Boolean) : super(context, autoInitialize) {
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */

        mContentResolver = context.getContentResolver();
    }

    constructor(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean) : super(context, autoInitialize, allowParallelSyncs) {
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver()
    }

    override fun onPerformSync(p0: Account?, p1: Bundle?, p2: String?, p3: ContentProviderClient?, p4: SyncResult?) {
        Log.d("SyncAdapter", "beginning")
        if (p1?.get("activity") == "Profile") {
            var userHobbyList = ArrayList<String>()
            var userId = DatabaseFilter.getSharedPreference(context).uid
            DatabaseFilter.getAllUserHobbies(context, userHobbyList, userId)
            DatabaseFilter.insertUserHobbiesList(context, userHobbyList, userId)
            PsychxWebService().updateUser(DatabaseFilter.getSharedPreference(context))
        } else {

        }
    }
    /*var chat= ChatHistory()
           chat.message= p1?.get("Message") as String?
           chat.message_id= p1?.get("Id") as Int
           chat.message_time= p1?.get("Time") as String?*/

    //PsychxWebService().getChatResponse(p1?.getSerializable("context") as ChatActivity, chat)
//        }
}