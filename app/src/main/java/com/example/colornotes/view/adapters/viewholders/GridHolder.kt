package com.example.colornotes.view.adapters.viewholders

import android.view.View
import com.example.colornotes.databinding.ItemGridBinding
import com.example.colornotes.view.adapters.BaseViewHolder
import com.example.colornotes.view.model.NoteData
import java.text.DateFormat


class GridHolder(view: View): BaseViewHolder(view){

    private val binding: ItemGridBinding

    init {
        binding = ItemGridBinding.bind(view)
    }

    override fun initView(noteData: NoteData) {
        binding.titleNoteGridItem.text = noteData.titleNote
        binding.textNoteGridItem.text  = noteData.textNote
        binding.dateNoteGridItem.text  = noteData.getDateByFormat(DateFormat.DAY_OF_YEAR_FIELD)
    }
}