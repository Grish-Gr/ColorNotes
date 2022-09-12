package com.example.colornotes.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.R
import com.example.colornotes.view.adapters.TypeHolder.*
import com.example.colornotes.view.adapters.viewholders.AllLineHolder
import com.example.colornotes.view.adapters.viewholders.GridHolder
import com.example.colornotes.view.adapters.viewholders.LineHolder
import com.example.colornotes.view.model.NoteData

typealias ActionClick = (noteData: NoteData) -> Unit
typealias ActionLongClick = (noteData: NoteData) -> Boolean

class MainAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    private var listNoteData: MutableList<NoteData> = emptyList<NoteData>().toMutableList()
    private val typeHolder: TypeHolder = TYPE_ITEM_ALL_LINE
    private var actionClick: ActionClick = { }
    private var actionLongClick: ActionLongClick = { false }

    fun setAction(actionClick: ActionClick, actionLongClick: ActionLongClick){
        this.actionClick = actionClick
        this.actionLongClick = actionLongClick
    }

    fun setListNoteData(listNote: List<NoteData>){
        listNoteData.clear()
        listNoteData.addAll(listNote)
        notifyDataSetChanged()
    }

    fun setNoteData(noteData: NoteData, position: Int = 0){
        listNoteData.add(position, noteData)
        notifyItemInserted(0)
    }

    fun deleteNoteData(noteData: NoteData){
        val position = listNoteData.lastIndexOf(noteData)
        listNoteData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder{
        Log.e("TAG", viewType.toString())
        return when(viewType){
            TYPE_ITEM_LINE.ordinal -> {
                LineHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(TYPE_ITEM_LINE.resLayout, parent, false)
                )
            }
            TYPE_ITEM_ALL_LINE.ordinal -> AllLineHolder(LayoutInflater
                .from(parent.context)
                .inflate(TYPE_ITEM_ALL_LINE.resLayout, parent, false)
            )
            else -> GridHolder(LayoutInflater
                .from(parent.context)
                .inflate(TYPE_ITEM_GRID.resLayout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.initView(listNoteData[position])
        holder.initAction(listNoteData[position], actionClick, actionLongClick)
    }

    override fun getItemViewType(position: Int): Int = when(typeHolder){
        TYPE_ITEM_LINE     -> TYPE_ITEM_LINE.ordinal
        TYPE_ITEM_ALL_LINE -> TYPE_ITEM_ALL_LINE.ordinal
        TYPE_ITEM_GRID     -> TYPE_ITEM_GRID.ordinal
    }

    override fun getItemCount(): Int = listNoteData.size

    private fun getPositionNote(noteData: NoteData): Int =
        listNoteData.lastIndexOf(noteData)
}