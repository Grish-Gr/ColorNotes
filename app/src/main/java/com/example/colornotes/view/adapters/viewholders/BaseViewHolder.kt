package com.example.colornotes.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.databinding.ItemGridBinding
import com.example.colornotes.view.model.NoteData
import java.text.DateFormat

abstract class BaseViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    abstract fun initView(noteData: NoteData)

    fun initAction(noteData: NoteData, actionClick: ActionClick, actionLongClick: ActionLongClick){
        view.setOnClickListener {
            actionClick(noteData)
        }
        view.setOnLongClickListener{
            actionLongClick(noteData)
        }
    }
}