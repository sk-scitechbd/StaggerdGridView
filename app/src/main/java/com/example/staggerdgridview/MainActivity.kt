package com.example.staggerdgridview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), StaggeredViewItemTouchListeners {

    private val adapter: RVAdapter = RVAdapter()
    private lateinit var fordoHomeItemTouchHelper: FordoHomeItemTouchHelper
    private val dataList = getGeneratedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rv_fordo_list)

        setUpStaggeredGridRecyclerViewAdapter(rv, adapter)
        fordoHomeItemTouchHelper = FordoHomeItemTouchHelper(adapter = adapter, recyclerViewItemTouchListeners = this, recyclerView = rv)
        adapter.setListToAdapter(dataList)


    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int, status: Boolean) {
        if (status) adapter.swapadapterPos(fromPosition, toPosition)
    }

    override fun onClearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dragCoordinates: ArrayList<Int>) {
        logThis("draggingCoordinates", "from: ${dragCoordinates.first()}, to: ${dragCoordinates.last()}")
        adapter.logAllListValue(dragCoordinates.first(), dragCoordinates.last())
    }

}
