package com.example.colornotes.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.view.adapters.TypeHolder.*
import com.example.colornotes.view.adapters.viewholders.AllLineHolder
import com.example.colornotes.view.adapters.viewholders.BaseViewHolder
import com.example.colornotes.view.adapters.viewholders.GridHolder
import com.example.colornotes.view.adapters.viewholders.LineHolder
import com.example.colornotes.view.model.NoteData

typealias ActionClick = (noteData: NoteData) -> Unit
typealias ActionLongClick = (view: View, noteData: NoteData) -> Boolean

class MainAdapter(private var typeHolder: TypeHolder): RecyclerView.Adapter<BaseViewHolder>() {

    private var listNoteData: MutableList<NoteData> = emptyList<NoteData>().toMutableList()
    private var actionClick: ActionClick = { }
    private var actionLongClick: ActionLongClick = { _, _ -> false }

    fun setAction(actionClick: ActionClick, actionLongClick: ActionLongClick){
        this.actionClick = actionClick
        this.actionLongClick = actionLongClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListNoteData(listNote: List<NoteData>){
        listNoteData.clear()
        listNoteData.addAll(listNote)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTypeItem(typeHolder: TypeHolder){
        this.typeHolder = typeHolder
        notifyDataSetChanged()
    }

    fun deleteNoteData(noteData: NoteData){
        val position = listNoteData.lastIndexOf(noteData)
        listNoteData.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
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
}