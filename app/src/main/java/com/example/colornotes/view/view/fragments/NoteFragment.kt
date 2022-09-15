package com.example.colornotes.view.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.colornotes.databinding.FragmentNoteBinding
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.view.ChipFactory
import com.example.colornotes.view.viewmodels.NoteViewModel
import kotlinx.coroutines.launch

class NoteFragment: BaseFragment() {

    private var currentNoteData: NoteData? = null
    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()

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
            lifecycleScope.launch {
                val res = if (currentNoteData == null){
                    viewModel.addNote(
                        getTitleNote(),
                        getTextNote(),
                        getCurrentColorGroup())
                } else {
                    currentNoteData?.titleNote = getTitleNote()
                    currentNoteData?.textNote  = getTextNote()
                    viewModel.updateNote(currentNoteData!!, getCurrentColorGroup())
                }
                res.join()
                backToParentFragment()
            }
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
        if (currentNoteData == null){
            binding.filterGroupChip.check(ChipFactory.DefaultId)
        } else {
            binding.filterGroupChip.check(currentNoteData!!.id.toInt())
        }
    }

    private fun fillNoteFromBundle(bundle: Bundle){
        Log.e("TAG", "Fill Note")
        val note: NoteData = bundle.getParcelable(TAG_PUT_DATA) ?: return
        currentNoteData = note
        binding.inputTitleNote.editText?.setText(note.titleNote)
        binding.inputTextNote.setText(note.textNote)
    }

    private fun getCurrentColorGroup(): Long =
        binding.filterGroupChip[binding.filterGroupChip.checkedChipId].tag as Long
    private fun getTitleNote(): String = binding.inputTitleNote.editText?.text.toString()
    private fun getTextNote(): String = binding.inputTextNote.text.toString()
}