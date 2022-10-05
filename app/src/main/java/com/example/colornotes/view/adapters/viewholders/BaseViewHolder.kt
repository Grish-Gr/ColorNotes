package com.example.colornotes.view.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.view.adapters.ActionClick
import com.example.colornotes.view.adapters.ActionLongClick
import com.example.colornotes.view.model.NoteData

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view){
    protected var layoutHolder: View = view

    abstract fun initView(noteData: NoteData)

    fun initAction(noteData: NoteData, actionClick: ActionClick, actionLongClick: ActionLongClick){
        layoutHolder.setOnClickListener {
            actionClick(noteData)
        }
        layoutHolder.setOnLongClickListener{
            actionLongClick(it, noteData)
        }
    }
}