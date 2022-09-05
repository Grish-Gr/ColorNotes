package com.example.colornotes.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.colornotes.databinding.ItemGridBinding
import com.example.colornotes.view.model.NoteData
import java.text.DateFormat

abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view){
    abstract fun initView(noteData: NoteData)
}