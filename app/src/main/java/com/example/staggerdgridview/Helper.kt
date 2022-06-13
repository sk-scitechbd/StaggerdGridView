package com.example.staggerdgridview

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun getGeneratedList(): ArrayList<FordoExtra> {
    val list = ArrayList<FordoExtra>()
    for (i in 0..13) {
        list.add(FordoExtra(i, i))
    }
    return list
}

fun <viewHolder, T : RecyclerView.Adapter<viewHolder>> setUpStaggeredGridRecyclerViewAdapter(view: RecyclerView, viewAdapter: T) {
    view.setHasFixedSize(true)
    view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    view.adapter = viewAdapter
}

fun logThis(tag: String? = "", message: String? = "") = Log.e(tag, "$message")
