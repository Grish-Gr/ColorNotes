package com.example.colornotes.view.view.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentNoteBinding
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.view.ChipFactory
import com.example.colornotes.view.viewmodels.AddNoteViewModel

class NoteFragment: BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionView()
        initLiveData()
        viewModel.getListGroup()
        Log.e("TAG", savedInstanceState.toString())
        if (arguments != null)
            fillNoteFromBundle(arguments as Bundle)
    }

    private fun initActionView(){
        binding.closeFragmentNoteAdd.setOnClickListener {
            backToParentFragment()
        }
        binding.successNote.setOnClickListener {
            viewModel.addNote(
                getTitleNote(),
                getTextNote(),
                getCurrentColorGroup())
            backToParentFragment()
        }
    }

    private fun initLiveData(){
        viewModel.listGroup.observe(viewLifecycleOwner){
            initChipGroup(it)
        }
    }

    private fun initChipGroup(listGroup: List<ColorGroupData>){
        listGroup.map { colorGroup ->
            binding.filterGroupChip.addView(
                ChipFactory.getChip(this.context as Context, colorGroup))
        }
        if (binding.filterGroupChip.checkedChipId == View.NO_ID)
            binding.filterGroupChip.check(ChipFactory.DefaultId)
    }

    private fun fillNoteFromBundle(bundle: Bundle){
        Log.e("TAG", "Fill Note")
        val note: NoteData = bundle.getParcelable(TAG_PUT_DATA) ?: return
        binding.filterGroupChip.check(note.colorGroup.id.toInt())
        binding.inputTitleNote.editText?.setText(note.titleNote)
        binding.inputTextNote.setText(note.textNote)
    }

    private fun getCurrentColorGroup(): Long =
        binding.filterGroupChip[binding.filterGroupChip.checkedChipId].tag as Long
    private fun getTitleNote(): String = binding.inputTitleNote.editText?.text.toString()
    private fun getTextNote(): String = binding.inputTextNote.text.toString()
}