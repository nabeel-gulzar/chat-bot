package com.example.sohaibrabbani.psychx;

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView

/**
 * Created by Amjad on 28-Nov-17.
 */

class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var mBody: TextView
    internal var mTime: TextView

    init {
        mBody = itemView.findViewById(R.id.text_message_body)
        mTime = itemView.findViewById(R.id.text_message_time)
    }
}
