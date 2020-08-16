package com.example.sohaibrabbani.psychx.sync

import Model.ChatHistory
import Model.DatabaseFilter
import android.accounts.Account
import android.content.*
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.example.sohaibrabbani.psychx.ChatActivity
import com.example.sohaibrabbani.psychx.Message
import com.example.sohaibrabbani.psychx.PsychxWebService
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*


public class SyncAdapterChat : AbstractThreadedSyncAdapter {


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
        Log.i("SyncAdapterChat","beginning")
        /*var chat= ChatHistory()
        chat.message= p1?.get("Message") as String?
        chat.message_id= p1?.get("Id") as Int
        chat.message_time= p1?.get("Message") as String?
        PsychxWebService().getChatResponse(ChatActivity(), chat)*/
    }
}