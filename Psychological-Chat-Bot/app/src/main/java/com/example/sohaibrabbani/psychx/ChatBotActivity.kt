package com.example.sohaibrabbani.psychx

import Model.ChatHistory
import Model.DatabaseFilter
import android.content.Context
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.sohaibrabbani.psychx.ChatBotActivity.MessageAdapter.Companion.ITEM_TYPE_RECEIVED
import com.example.sohaibrabbani.psychx.ChatBotActivity.MessageAdapter.Companion.ITEM_TYPE_SENT
import com.example.sohaibrabbani.psychx.provider.SyncAccount
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat_bot.*
import java.text.SimpleDateFormat
import java.util.*

class ChatBotActivity : AppCompatActivity() {

    internal var flagFab: Boolean? = true
    lateinit var ACCOUNT_TYPE: String
    lateinit var AUTHORITY_CHAT: String
    private val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

    private var messageData = ArrayList<Message>()

     override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_chat_bot)
      recyclerView.adapter = MessageAdapter(this, messageData)
      recyclerView.layoutManager = LinearLayoutManager(this)
      AUTHORITY_CHAT = getString(R.string.content_authority_chat)
      ACCOUNT_TYPE = getString(R.string.sync_account_type)

  }
  fun sendMessage(view: View)
  {
      var syncAcc: SyncAccount = SyncAccount(ACCOUNT_TYPE)
      syncAcc.CreateSyncAccount(this)
      //syncAcc.RunSyncing(AUTHORITY_CHAT)

      var chat = ChatHistory()
      chat.sender_id = DatabaseFilter.getSharedPreference(this).uid
      chat.message = editText.text.toString()
      chat.message_time = formatter.format(Date())

      messageData.add(Message(chat.message, chat.message_time, false))
      recyclerView.adapter.notifyDataSetChanged()

      //PsychxWebService().getChatResponse(this, chat)
      editText.text.clear()
  }
  fun generateResponse(response: String) {
      messageData.add(Message(response, formatter.format(Date()), true))
      recyclerView.adapter.notifyDataSetChanged()
  }
    class MessageAdapter(context: Context, list: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        internal var list: List<Message> = ArrayList()
        internal var inflater: LayoutInflater

        init {
            inflater = LayoutInflater.from(context)
            this.list = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView: View

            if (viewType == ITEM_TYPE_SENT) {
                itemView = inflater.inflate(R.layout.activity_message_sent, parent, false)
            } else
            //(viewType == ITEM_TYPE_RECEIVED)
            {
                itemView = inflater.inflate(R.layout.activity_message_received, parent, false)
            }
            //        itemView.setOnLongClickListener(onLongClickListener);

            return SentMessageHolder(itemView) // view holder for normal items
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val m = list[position]
            (holder as SentMessageHolder).mBody.text = m.mBody
            holder.mTime.text = m.mTime
            holder.mTime.visibility = View.GONE
            holder.mBody.setOnLongClickListener {
                holder.mTime.visibility = View.VISIBLE
                Log.d("Adapter", "OnClick")
                false
            }

            holder.mBody.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    holder.mTime.visibility = View.GONE
                }
                false
            }

        }


        override fun getItemViewType(position: Int): Int {
            return if (list[position].mSent) {
                ITEM_TYPE_SENT
            } else {
                ITEM_TYPE_RECEIVED
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        companion object {
            //    private final View.OnLongClickListener onLongClickListener= new View.OnLongClickListener() {
            //        @Override
            //        public boolean onLongClick(View v) {
            //            Log.d("Adapter", "Long Clicked");
            //            return false;
            //        }
            //    };
            val ITEM_TYPE_SENT = 0
            val ITEM_TYPE_RECEIVED = 1
        }
    }
}
