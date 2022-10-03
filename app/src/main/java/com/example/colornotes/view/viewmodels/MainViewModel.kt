package com.example.colornotes.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.adapters.TypeHolder
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.model.SqlRepository
import com.example.colornotes.view.view.filter.FilterSetting
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _listNote = MutableLiveData<List<NoteData>>()
    val listDataNote: LiveData<List<NoteData>> = _listNote
    var filterSetting = FilterSetting.getDefaultFilterSetting()

    fun getFilterView(): TypeHolder = when(filterSetting.ordinalViewFilter){
        0 -> TypeHolder.TYPE_ITEM_LINE
        1 -> TypeHolder.TYPE_ITEM_ALL_LINE
        else -> TypeHolder.TYPE_ITEM_GRID
    }

    fun deleteNote(noteData: NoteData) = viewModelScope.launch {
        SqlRepository.deleteNoteData(noteData)
    }

    fun getListNote(colorIdFilter: Long? = null, byEarly: Boolean = true) = viewModelScope.launch {
        if (colorIdFilter == null) {
            _listNote.postValue(SqlRepository.getListNoteData(byEarly))
        } else {
            _listNote.postValue(SqlRepository.getListNoteData(colorIdFilter, byEarly))
        }
    }
}