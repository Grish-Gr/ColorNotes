package com.example.colornotes.view.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.example.colornotes.databinding.FragmentNoteAddBinding
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.view.ChipFactory
import com.example.colornotes.view.viewmodels.AddNoteViewModel

class AddNoteFragment: BaseFragment() {

    private lateinit var binding: FragmentNoteAddBinding
    private val viewModel: AddNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionView()
        initLiveData()
        viewModel.getListGroup()
    }

    private fun initActionView(){
        binding.closeFragmentNoteAdd.setOnClickListener {
            backToParentFragment()
        }
        binding.successNote.setOnClickListener {
            viewModel.addNote(
                getTitleNote(),
                getTextNote(),
                getCurrentColorGroup()
            )
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
    }

    private fun getCurrentColorGroup(): Long =
        binding.filterGroupChip[binding.filterGroupChip.checkedChipId]
            .tag as Long

    private fun getTitleNote(): String = binding.inputTitleNote.editText?.text.toString()
    private fun getTextNote(): String = binding.inputTextNote.text.toString()
}