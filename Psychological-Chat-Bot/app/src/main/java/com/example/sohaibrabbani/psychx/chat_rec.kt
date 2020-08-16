package com.example.sohaibrabbani.psychx

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView


class chat_rec(itemView: View) : RecyclerView.ViewHolder(itemView) {


    internal var leftText: TextView
    internal var rightText: TextView

    init {

        leftText = itemView.findViewById(R.id.leftText) as TextView
        rightText = itemView.findViewById(R.id.rightText) as TextView


    }
}
