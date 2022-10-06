package com.example.colornotes.view.adapters.viewholders

import android.view.View
import com.example.colornotes.databinding.ItemAllLineBinding
import com.example.colornotes.view.model.NoteData
import java.text.DateFormat

class AllLineHolder(view: View): BaseViewHolder(view) {
    private val binding: ItemAllLineBinding

    init {
        binding = ItemAllLineBinding.bind(view)
        layoutHolder = binding.cardAllLineItem
    }
    override fun initView(noteData: NoteData) {
        binding.titleNoteAllLineItem.text = noteData.titleNote
        binding.textNoteAllLineItem.text  = noteData.textNote
        binding.dateNoteAllLineItem.text  = noteData.getDateByFormat()
        binding.indicatorNoteAllLineItem.dividerColor = noteData.colorGroup.getColor()
    }
}