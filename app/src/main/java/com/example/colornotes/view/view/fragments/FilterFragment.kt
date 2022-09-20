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
import android.widget.SpinnerAdapter
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
        val filterSetting = arguments?.getParcelable(BaseFragment.KEY_PUT_DATA)
                            ?: FilterSetting.getDefaultFilterSetting()
        viewModel.filterSetting = filterSetting
        initSortingFilter()
        initLiveData()
        viewModel.getListGroup()
        fillFilterSetting()
    }

    private fun initSortingFilter(){
        val adapter = ArrayAdapter<String>(
            this.context as Context,
            layout.support_simple_spinner_dropdown_item)
        adapter.addAll(SortingFilter.values().map { it.title })
        binding.spinnerSortFilter.adapter = adapter
    }

    private fun initLiveData(){
        viewModel.listGroup.observe(viewLifecycleOwner){
            it.map { groupData ->
                binding.filterGroupChip.addView(
                    ChipFactory.getChip(context as Context, groupData))
            }
            binding.filterGroupChip.check(viewModel.filterSetting.filterGroup?.toInt() ?: ChipFactory.DefaultId)
        }
    }

    private fun fillFilterSetting(){
        binding.spinnerSortFilter.setSelection(viewModel.filterSetting.filterSorting)
        binding.filterViewList.check(getIdButtonFilterView(viewModel.filterSetting.filterView))
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val result = Bundle()
        result.putParcelable(KEY_FILTER_FRAGMENT, getFilterSetting())
        setFragmentResult(BaseFragment.KEY_RESULT_FRAGMENT, result)
    }

    private fun getFilterSetting(): FilterSetting =
        FilterSetting(
            getCurrentFilterSort(),
            getCurrentFilterView(),
            getCurrentFilterGroup()
        )

    //region TODO This in Sealed or Enum class
    private fun getCurrentFilterView(): Int{
        return when(binding.filterViewList.checkedRadioButtonId){
            R.id.line_list_filter -> 0
            R.id.line_list_all_filter -> 1
            R.id.grid_list_filter -> 2
            else -> -1
        }
    }

    private fun getIdButtonFilterView(filterView: Int): Int = when(filterView){
        0 -> R.id.line_list_filter
        1 -> R.id.line_list_all_filter
        else -> R.id.grid_list_filter
    }
    //endregion

    private fun getCurrentFilterGroup(): Long?{
        val indexCheckGroup = binding.filterGroupChip.checkedChipId
        return if (indexCheckGroup == View.NO_ID){
            null
        } else {
            binding.filterGroupChip[indexCheckGroup].tag as Long
        }
    }

    private fun getCurrentFilterSort(): Int = binding.spinnerSortFilter.selectedItemPosition

    companion object{
        const val KEY_FILTER_FRAGMENT = "KeyFilterFragment"
    }
}