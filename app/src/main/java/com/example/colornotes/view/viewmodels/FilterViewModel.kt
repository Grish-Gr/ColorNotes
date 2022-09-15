package com.example.colornotes.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.SqlRepository
import kotlinx.coroutines.launch


class FilterViewModel: ViewModel() {

    private var filterData = FilterData()
    private val _listGroup = MutableLiveData<List<ColorGroupData>>()
    val listGroup: LiveData<List<ColorGroupData>> = _listGroup

    fun setFilterView(filterView: Int){
        filterData.filterView = filterView
    }

    fun setFilterSorting(filterSorting: Int){
        filterData.filterSorting = filterSorting
    }

    fun setFilterGroup(filterGroup: Int){
        filterData.filterGroup = filterGroup
    }

    fun getListGroup() = viewModelScope.launch {
        _listGroup.postValue(SqlRepository.getListColorGroupData())
    }

    fun getFilterData() = filterData
}