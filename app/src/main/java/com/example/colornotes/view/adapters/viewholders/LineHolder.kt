package com.example.colornotes.view.adapters.viewholders

import android.view.View
import com.example.colornotes.databinding.ItemLineBinding
import com.example.colornotes.view.adapters.BaseViewHolder
import com.example.colornotes.view.model.NoteData

class LineHolder(view: View): BaseViewHolder(view) {
    private val binding: ItemLineBinding

    init {
        binding = ItemLineBinding.bind(view)
    }

    override fun initView(noteData: NoteData) {
        binding.titleNoteLineItem.text = noteData.titleNote
        binding.textNoteLineItem.text  = noteData.textNote
    }
}