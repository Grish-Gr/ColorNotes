package com.example.colornotes.view.view.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentBottomFilterBinding
import com.example.colornotes.view.view.ChipFactory
import com.example.colornotes.view.view.filter.FilterSetting
import com.example.colornotes.view.view.filter.SortingFilter
import com.example.colornotes.view.viewmodels.FilterViewModel
import com.google.android.material.R.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        initSortingFilter()
        initLiveData()
        viewModel.getListGroup()
    }

    private fun initSortingFilter(){
        val adapter = ArrayAdapter<String>(
            this.context as Context,
            layout.support_simple_spinner_dropdown_item)
        val listSoringFilters = SortingFilter.values().map { it.title }
        adapter.addAll(listSoringFilters)
        binding.sortFilterList.setAdapter(adapter)
        //TODO !Get Selection Item!
        /*binding.sortFilterList.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }*/
    }

    private fun initLiveData(){
        viewModel.listGroup.observe(viewLifecycleOwner){
            it.map { groupData ->
                binding.filterGroupChip.addView(
                    ChipFactory.getChip(context as Context, groupData))
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val result = Bundle()
        result.putParcelable(KEY_FILTER_FRAGMENT, getFilterSetting())
        setFragmentResult(BaseFragment.KEY_RESULT_FRAGMENT, result)
    }

    private fun getFilterSetting(): FilterSetting =
        FilterSetting(
            binding.sortFilterList.listSelection,
            getCurrentFilterView(),
            getCurrentFilterGroup()
        )

    private fun getCurrentFilterView(): Int{
        return when(binding.filterViewList.checkedRadioButtonId){
            R.id.line_list_filter -> 0
            R.id.line_list_all_filter -> 1
            R.id.grid_list_filter -> 2
            else -> -1
        }
    }

    private fun getCurrentFilterGroup(): Long?{
        val indexCheckGroup = binding.filterGroupChip.checkedChipId
        return if (indexCheckGroup == -1){
            null
        } else {
            binding.filterGroupChip[indexCheckGroup].tag as Long
        }
    }

    companion object{
        const val TAG_FILTER_FRAGMENT = "TagFilterFragment"
        const val KEY_FILTER_FRAGMENT = "KeyFilterFragment"
    }
}