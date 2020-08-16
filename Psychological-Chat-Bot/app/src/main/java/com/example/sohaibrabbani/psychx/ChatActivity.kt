package com.example.sohaibrabbani.psychx


import Model.ChatHistory
import Model.DatabaseFilter
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.sohaibrabbani.psychx.provider.SyncAccount
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.R.string.ok





class ChatActivity : AppCompatActivity() {


    lateinit var rv: RecyclerView
    private val NOTIFICATION_ID = 1010
    lateinit var ACCOUNT_TYPE: String
    lateinit var AUTHORITY_CHAT: String
    var newChat = true
    private val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    var chats= java.util.ArrayList<ChatHistory>()
    var messageData = ArrayList<Message>()

    companion object {
        lateinit var instance: ChatActivity
        //var foo=this
    }

    init {
        //val foo=this

        instance = this
    }

    override fun onStart() {
        super.onStart()
        newChat = true
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if(!isConnected)
        {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Internet Connection")
            builder.setMessage("Internet connection is required to send message. Try again after connecting to the internet.")
            builder.setCancelable(false)
            builder.setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
                finish()
            })
            var dialog = builder.create()
            dialog.show()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        rv = findViewById(R.id.recView)
        rv.adapter = ChatAdapter(this, messageData)
        rv.layoutManager = LinearLayoutManager(this)
        AUTHORITY_CHAT = getString(R.string.content_authority_profile)
        ACCOUNT_TYPE = getString(R.string.sync_account_type)

        DatabaseFilter.loadAllMessages(this, messageData)


//        for (chat in chats)
//        {
//            if(!chat.message.isEmpty())
//                messageData.add(Message(chat.message, chat.message_time, false))
//            if(!chat.response.isEmpty())
//                messageData.add(Message(chat.message, chat.message_time, true))
//        }
        rv.adapter.notifyDataSetChanged()



    }

    fun sendMessage(view: View)
    {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

        if(!isConnected)
        {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Internet Connection")
            builder.setMessage("Internet connection is required to send message. Try again after connecting to the internet.")
            builder.setCancelable(false)
            builder.setPositiveButton("Okay", DialogInterface.OnClickListener { dialog, id ->
                finish()
            })
            var dialog = builder.create()
            dialog.show()
        }

        instance=this
        var syncAcc: SyncAccount = SyncAccount(ACCOUNT_TYPE)
        syncAcc.CreateSyncAccount(this)
        syncAcc.RunSyncing(AUTHORITY_CHAT,"Chat"/*,edittext_chatbox.text.toString(),formatter.format(Date()),DatabaseFilter.getSharedPreference(this).uid,"Chat",this*/)

        var chat = ChatHistory()

        chat.sender_id = DatabaseFilter.getSharedPreference(this).uid
        chat.message = edittext_chatbox.text.toString()
        chat.message_time = formatter.format(Date())

        messageData.add(Message(chat.message, chat.message_time, false))
        rv.adapter.notifyDataSetChanged()


        if(newChat) {
            PsychxWebService.SESSION_ID= "new"
            newChat = false
        }
        PsychxWebService().getChatResponse(this,chat)
        edittext_chatbox.text.clear()

    }

    fun generateResponse(response: String) {
        messageData.add(Message(response, formatter.format(Date()), true))
        rv.adapter.notifyDataSetChanged()
    }

}
