package com.example.colornotes.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.R
import com.example.colornotes.view.adapters.viewholders.AllLineHolder
import com.example.colornotes.view.adapters.viewholders.GridHolder
import com.example.colornotes.view.adapters.viewholders.LineHolder
import com.example.colornotes.view.model.NoteData

class MainAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    var listNoteData: List<NoteData> = emptyList<NoteData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun updateData(noteData: NoteData, typeUpdate: TypeUpdate){
        when(typeUpdate){
            TypeUpdate.DELETE -> { notifyItemRemoved(getPositionNote(noteData)) }
            TypeUpdate.UPDATE -> { notifyItemInserted(getPositionNote(noteData)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = when(viewType as TypeHolder){
        TypeHolder.TYPE_ITEM_LINE -> LineHolder(LayoutInflater
            .from(parent.context)
            .inflate(TypeHolder.TYPE_ITEM_LINE.resLayout, parent)
        )
        TypeHolder.TYPE_ITEM_ALL_LINE -> AllLineHolder(LayoutInflater
            .from(parent.context)
            .inflate(TypeHolder.TYPE_ITEM_ALL_LINE.resLayout, parent)
        )
        TypeHolder.TYPE_ITEM_GRID -> GridHolder(LayoutInflater
            .from(parent.context)
            .inflate(TypeHolder.TYPE_ITEM_GRID.resLayout, parent)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    private fun getPositionNote(noteData: NoteData): Int =
        listNoteData.lastIndexOf(noteData)
}

enum class TypeHolder(val resLayout: Int){
    TYPE_ITEM_LINE(R.layout.item_line),
    TYPE_ITEM_ALL_LINE(R.layout.item_all_line),
    TYPE_ITEM_GRID(R.layout.item_grid)
}

enum class TypeUpdate(){
    DELETE,
    UPDATE
}