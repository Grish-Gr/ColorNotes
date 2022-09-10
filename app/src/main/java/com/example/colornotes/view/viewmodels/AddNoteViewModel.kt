package com.example.colornotes.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.model.SqlRepository
import kotlinx.coroutines.launch
import java.util.*

class AddNoteViewModel: ViewModel() {

    private val _listGroup = MutableLiveData<List<ColorGroupData>>()
    val listGroup: LiveData<List<ColorGroupData>> = _listGroup

    fun addNote(titleNote: String, textNote: String, idColorGroup: Long) = viewModelScope.launch {
        SqlRepository.createNoteData(NoteData(
            0,
            titleNote,
            textNote,
            Calendar.getInstance().timeInMillis,
            idColorGroup
        ))
    }

    fun getListGroup() = viewModelScope.launch {
        _listGroup.postValue(SqlRepository.getListColorGroupData())
    }
}