package com.example.colornotes.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.SqlRepository
import kotlinx.coroutines.launch


class FilterViewModel: ViewModel() {

    private val _listGroup = MutableLiveData<List<ColorGroupData>>()
    val listGroup: LiveData<List<ColorGroupData>> = _listGroup

    fun getListGroup() = viewModelScope.launch {
        _listGroup.postValue(SqlRepository.getListColorGroupData())
    }
}