package com.example.staggerdgridview

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("setTextSafe")
    fun setTextSafe(view: TextView, fordo: FordoExtra) {
        fordo.let {
            val t = "adapterPos: ${it.adapterPos}, DATAid: ${it.id}"
            view.text = t
        }
    }

}