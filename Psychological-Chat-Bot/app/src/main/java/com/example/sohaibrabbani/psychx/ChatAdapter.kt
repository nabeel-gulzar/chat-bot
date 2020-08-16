package com.example.sohaibrabbani.psychx;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sohaibrabbani.psychx.R

import java.util.ArrayList


class ChatAdapter(context: Context, list: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        {
            itemView = inflater.inflate(R.layout.activity_message_received, parent, false)
        }
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
