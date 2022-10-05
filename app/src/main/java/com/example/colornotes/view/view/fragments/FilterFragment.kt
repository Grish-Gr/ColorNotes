package com.example.colornotes.view.view.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentBottomFilterBinding
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.view.filter.FilterSetting
import com.example.colornotes.view.view.filter.SortFilter
import com.example.colornotes.view.view.filter.ViewFilter
import com.example.colornotes.view.viewmodels.FilterViewModel
import com.google.android.material.R.layout
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
        viewModel.filterSetting = arguments?.getParcelable(BaseFragment.KEY_PUT_DATA)
            ?: FilterSetting.getDefaultFilterSetting()
        initViewFilterGroup()
        initSortFilterSpinner()
        initLiveData()
        viewModel.getListGroup()
    }

    private fun initSortFilterSpinner(){
        val adapter = ArrayAdapter<String>(
            this.context as Context,
            layout.support_simple_spinner_dropdown_item)
        adapter.addAll(SortFilter.arraySortFilters.map { it.title })
        binding.spinnerSortFilter.adapter = adapter
        binding.spinnerSortFilter.setSelection(viewModel.filterSetting.sortFilter.ordinal)
    }

    private fun initViewFilterGroup(){
        ViewFilter.arrayViewFilters.map { viewFilter ->
            binding.groupViewFilter.addView(getViewFilterRadioButton(viewFilter))
            if (viewFilter.ordinal == viewModel.filterSetting.viewFilter.ordinal){
                binding.groupViewFilter.check(viewFilter.ordinal)
            }
        }
    }

    private fun initLiveData(){
        viewModel.listGroup.observe(viewLifecycleOwner){
            binding.filterGroupChip.removeAllViews()
            it.map { groupData ->
                binding.filterGroupChip.addView(getColorGroupChip(groupData))
            }
            if (viewModel.filterSetting.filterGroup != null)
                binding.filterGroupChip.check(viewModel.filterSetting.filterGroup?.toInt()!!)
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
            getCurrentFilterSort(),
            getCurrentFilterView(),
            getCurrentFilterGroup()?.id
        )

    private fun getCurrentFilterGroup(): ColorGroupData?{
        val indexCheckGroup = binding.filterGroupChip.checkedChipId
        return if (indexCheckGroup == View.NO_ID){
            null
        } else {
            binding.filterGroupChip[indexCheckGroup]
                .getTag(R.string.tag_chip_color_group) as ColorGroupData
        }
    }

    private fun getCurrentFilterView(): ViewFilter =
        binding.groupViewFilter[binding.groupViewFilter.checkedRadioButtonId]
            .getTag(R.string.tag_button_view_filter) as ViewFilter

    private fun getCurrentFilterSort(): SortFilter =
        SortFilter.arraySortFilters[binding.spinnerSortFilter.selectedItemPosition]

    companion object{
        const val KEY_FILTER_FRAGMENT = "KeyFilterFragment"
    }
}