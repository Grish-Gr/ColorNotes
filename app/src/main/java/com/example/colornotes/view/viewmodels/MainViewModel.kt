package com.example.colornotes.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.adapters.TypeHolder
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.model.SqlRepository
import com.example.colornotes.view.view.filter.FilterSetting
import com.example.colornotes.view.view.filter.SortFilter
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _listNote = MutableLiveData<List<NoteData>>()
    val listDataNote: LiveData<List<NoteData>> = _listNote
    var filterSetting = FilterSetting.getDefaultFilterSetting()

    fun getFilterView(): TypeHolder = when(filterSetting.viewFilter.ordinal){
        0 -> TypeHolder.TypeItemLine
        1 -> TypeHolder.TypeItemAllLine
        else -> TypeHolder.TypeItemGrid
    }

    fun deleteNote(noteData: NoteData) = viewModelScope.launch {
        SqlRepository.deleteNoteData(noteData)
    }

    fun getListNote(colorIdFilter: Long? = null, sortFilter: SortFilter = SortFilter.TimeEarly) = viewModelScope.launch {
        if (colorIdFilter == null) {
            _listNote.postValue(SqlRepository.getListNoteData(sortFilter))
        } else {
            _listNote.postValue(SqlRepository.getListNoteData(sortFilter, colorIdFilter))
        }
    }
}