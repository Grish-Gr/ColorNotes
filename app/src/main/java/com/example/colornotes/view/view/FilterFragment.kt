package com.example.colornotes.view.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.colornotes.databinding.FragmentBottomFilterBinding
import com.example.colornotes.databinding.FragmentSettingsBinding
import com.example.colornotes.view.adapters.TypeHolder
import com.example.colornotes.view.viewmodels.ChipFactory
import com.example.colornotes.view.viewmodels.FilterViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class FilterFragment: BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomFilterBinding
    private val viewModel: FilterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listGroup.observe(viewLifecycleOwner){
            it.map { colorGroupData ->
                binding.filterGroupChip.addView(ChipFactory.getFilterChip(this.context as Context, colorGroupData))
            }
        }
    }

    private fun initView(){
    }

    companion object{
        const val TAG_FILTER_FRAGMENT = "TagFilterFragment"

    }
}