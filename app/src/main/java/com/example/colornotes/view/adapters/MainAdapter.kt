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
            TypeItemLine.ordinal -> {
                LineHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(TypeItemLine.resLayout, parent, false)
                )
            }
            TypeItemAllLine.ordinal -> AllLineHolder(LayoutInflater
                .from(parent.context)
                .inflate(TypeItemAllLine.resLayout, parent, false)
            )
            else -> GridHolder(LayoutInflater
                .from(parent.context)
                .inflate(TypeItemGrid.resLayout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.initView(listNoteData[position])
        holder.initAction(listNoteData[position], actionClick, actionLongClick)
    }

    override fun getItemViewType(position: Int): Int = when(typeHolder){
        TypeItemLine    -> TypeItemLine.ordinal
        TypeItemAllLine -> TypeItemAllLine.ordinal
        TypeItemGrid    -> TypeItemGrid.ordinal
    }

    override fun getItemCount(): Int = listNoteData.size
}