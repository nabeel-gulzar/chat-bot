package com.example.sohaibrabbani.psychx.provider

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import com.example.sohaibrabbani.psychx.ChatActivity
import com.example.sohaibrabbani.psychx.parcel

class SyncAccount{

    lateinit var AUTHORITY: String
    lateinit var ACCOUNT_TYPE: String
    val ACCOUNT = "PsychX"
    lateinit var mAccount: Account
    var mResolver: ContentResolver? = null

    constructor(accountType: String)
    {
        ACCOUNT_TYPE=accountType
    }

    fun CreateSyncAccount(context: Context): Account {

        val newAccount = Account(
                ACCOUNT, ACCOUNT_TYPE)

        val accountManager = context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {

        } else {

        }
        mAccount=newAccount
        return newAccount
    }
    fun RunSyncing(authority: String,activity: String/*,message: String,time: String,id: Int,activityS: String,context: ChatActivity*/)
    {

        AUTHORITY=authority
        val settingsBundle = Bundle()
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true)
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
        settingsBundle.putString("activity",activity)
/*
        val activity=parcel()
        activity.obj=context

        settingsBundle.putString(
                "Message",message)
        settingsBundle.putString(
                "Time",time)
        settingsBundle.putInt(
                "Id",id)
        settingsBundle.putString(
                "activity",activityS)
        settingsBundle.putSerializable("context",activity)
*/
        //settingsBundle.putParcelable("context",context)
        ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true)
        ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle)
    }
}