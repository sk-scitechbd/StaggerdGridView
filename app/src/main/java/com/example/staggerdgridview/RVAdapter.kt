package com.example.staggerdgridview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.staggerdgridview.databinding.ListItemFordoRectangleViewBinding
import java.util.*


class RVAdapter : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    private val tag = RVAdapter::class.java.simpleName.toString()
    private var mArray = ArrayList<FordoExtra>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_fordo_rectangle_view, parent, false)
    )

    override fun getItemCount(): Int = mArray.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.fordoExtra = mArray[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListToAdapter(mArray: List<FordoExtra>) {
        if (mArray.isNotEmpty()) {
            this.mArray = ArrayList()
            this.mArray.addAll(mArray)
            notifyDataSetChanged()
        }
    }

    fun swapadapterPos(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Log.d("i", "$i")
                Collections.swap(mArray, i, i + 1)
                mArray[i].adapterPos = i
                mArray[i + 1].adapterPos = i + 1
            }
        }
        if (fromPosition > toPosition) {
            for (i in fromPosition downTo toPosition + 1) {
                Log.d("i", "$i")
                Collections.swap(mArray, i, i - 1)
                mArray[i].adapterPos = i
                mArray[i - 1].adapterPos = i - 1
            }
        }
    }

    fun logAllListValue(positionChangedFrom: Int, positionChangedTo: Int) {
//        mArray.forEachIndexed { i, data -> logThis("index:$i", "data {${data}}") }
        if (positionChangedFrom < positionChangedTo) notifyItemRangeChanged(positionChangedFrom, (positionChangedTo + 1) - positionChangedFrom)
        if (positionChangedFrom > positionChangedTo) notifyItemRangeChanged(positionChangedTo, (positionChangedFrom + 1) - positionChangedTo)
    }

    inner class ViewHolder(val binding: ListItemFordoRectangleViewBinding) : RecyclerView.ViewHolder(binding.root)

    interface FordoHomeOnAdapterItemClickListener {
        fun onAdapterItemClick(eventTime: Long = System.currentTimeMillis(), fordoExtra: FordoExtra, adapterPos: Int, binding: ListItemFordoRectangleViewBinding)
        fun onAdapterItemLongClick(eventTime: Long = System.currentTimeMillis(), fordoExtra: FordoExtra, adapterPos: Int, binding: ListItemFordoRectangleViewBinding)
        fun onAllSelectionCleared()
    }

}

data class FordoExtra(var adapterPos: Int = 0, var id: Int)

