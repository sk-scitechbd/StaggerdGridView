package com.example.staggerdgridview

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView


class FordoHomeItemTouchHelper(
    adapter: RVAdapter, recyclerView: RecyclerView, dragDirs: Int = UP or START or END or DOWN or LEFT or RIGHT, swipeDirs: Int = 0,
    recyclerViewItemTouchListeners: StaggeredViewItemTouchListeners,
) : SimpleCallback(dragDirs, swipeDirs) {
    private val tag = "FordoHomeItemTouchHelper"
    private var draggable = true
    private var adapter: RVAdapter
    private var recyclerView: RecyclerView
    private var dragDirs = 0
    private var swipeDirs = 0
    private var recyclerViewItemTouchListeners: StaggeredViewItemTouchListeners
    private var itemTouchHelper: ItemTouchHelper

    private var draggedCoordinates = ArrayList<Int>()

    init {
        this.adapter = adapter
        this.dragDirs = dragDirs
        this.swipeDirs = swipeDirs
        this.recyclerView = recyclerView
        this.recyclerViewItemTouchListeners = recyclerViewItemTouchListeners
        this.itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(this.recyclerView)
    }

    override fun onMoved(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)

        if (viewHolder.adapterPosition != target.adapterPosition) {
            draggedCoordinates.add(fromPos)
            draggedCoordinates.add(toPos)
            adapter.notifyItemMoved(fromPos, toPos)
            recyclerViewItemTouchListeners.onItemMoved(fromPos, toPos, fromPos != toPos)
        }
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        recyclerViewItemTouchListeners.onItemSwiped(viewHolder.adapterPosition, direction)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        recyclerViewItemTouchListeners.onClearView(recyclerView, viewHolder, draggedCoordinates)
        draggedCoordinates = ArrayList()
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        recyclerViewItemTouchListeners.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        this.dragDirs = if (draggable) UP or START or END or DOWN else 0
        return makeMovementFlags(dragDirs, swipeDirs)
    }

    fun setDraggable(draggable: Boolean = true) {
        this.draggable = draggable
    }


}

interface StaggeredViewItemTouchListeners {
    fun onItemMoved(fromPosition: Int, toPosition: Int, status: Boolean = false)
    fun onItemSwiped(itemPosition: Int, direction: Int) {}
    fun onClearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dragCoordinates: ArrayList<Int>) {}
    fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {}
}