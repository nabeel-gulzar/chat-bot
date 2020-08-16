package com.example.sohaibrabbani.psychx

/**
 * Created by sohaibrabbani on 12/29/2017.
 */
interface ArrayAdapterClickHelper {
    fun clickDelegate(position: Int, checked: Boolean)

    fun onPositiveButtonClick(selectedData: ArrayList<Int>)
}