package com.example.colornotes.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.R
import com.example.colornotes.view.adapters.TypeHolder.*
import com.example.colornotes.view.adapters.viewholders.AllLineHolder
import com.example.colornotes.view.adapters.viewholders.GridHolder
import com.example.colornotes.view.adapters.viewholders.LineHolder
import com.example.colornotes.view.model.NoteData

class MainAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    private var listNoteData: MutableList<NoteData> = emptyList<NoteData>().toMutableList()
    private val typeHolder: TypeHolder = TYPE_ITEM_ALL_LINE

    fun setListNoteData(listNote: List<NoteData>){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = when(viewType as TypeHolder){
        TYPE_ITEM_LINE -> LineHolder(LayoutInflater
            .from(parent.context)
            .inflate(TYPE_ITEM_LINE.resLayout, parent)
        )
        TYPE_ITEM_ALL_LINE -> AllLineHolder(LayoutInflater
            .from(parent.context)
            .inflate(TYPE_ITEM_ALL_LINE.resLayout, parent)
        )
        TYPE_ITEM_GRID -> GridHolder(LayoutInflater
            .from(parent.context)
            .inflate(TYPE_ITEM_GRID.resLayout, parent)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.initView(listNoteData[position])
    }

    override fun getItemId(position: Int): Long = when(typeHolder){
        TYPE_ITEM_LINE     -> TYPE_ITEM_LINE.ordinal.toLong()
        TYPE_ITEM_ALL_LINE -> TYPE_ITEM_ALL_LINE.ordinal.toLong()
        TYPE_ITEM_GRID     -> TYPE_ITEM_GRID.ordinal.toLong()
    }

    override fun getItemCount(): Int = listNoteData.size

    private fun getPositionNote(noteData: NoteData): Int =
        listNoteData.lastIndexOf(noteData)
}