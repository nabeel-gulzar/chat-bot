package com.example.sohaibrabbani.psychx

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.txt_hobby


/**
 * Created by sohaibrabbani on 12/29/2017.
 */
class MultipleCheckBoxAdapter : ArrayAdapter<String> {

    lateinit var con: Context

    var layoutResourceId: Int = 0

    lateinit var input: List<String>
    lateinit var inputDb: List<String>

    lateinit var clickHelper: ArrayAdapterClickHelper

    constructor(context: Context, layoutResourceId: Int, input: List<String>, inputDb: List<String>, clickHelper: ArrayAdapterClickHelper) : super(context, layoutResourceId, input) {

        this.con = context

        this.layoutResourceId = layoutResourceId

        this.input = input
        this.inputDb = inputDb
        /*inputDb= ArrayList()
        inputDb.add("Hello")
        inputDb.add("Baby")
        inputDb.add("Yes")*/
        this.clickHelper = clickHelper

    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(layoutResourceId, null)

        val checkbox = view.findViewById(R.id.checkBox) as CheckedTextView

        checkbox.setText(input[position])

        if (inputDb.contains(input[position])) {
            checkbox.isChecked = true
            checkbox.checkMarkDrawable =  ContextCompat.getDrawable(context,R.drawable.ic_checked)
        }
        else
        {
            checkbox.isChecked = false
            checkbox.checkMarkDrawable = null
        }

        checkbox.setOnClickListener {
            var drawable: Drawable
            checkbox.isChecked= (!checkbox.isChecked)
            if(checkbox.checkMarkDrawable!=null)
            {
                drawable=checkbox.checkMarkDrawable
                checkbox.checkMarkDrawable=null
            }
            else {
                 checkbox.checkMarkDrawable= ContextCompat.getDrawable(context,R.drawable.ic_checked)
            }
            clickHelper.clickDelegate(position, (checkbox).isChecked)
        }

        return view

    }
}