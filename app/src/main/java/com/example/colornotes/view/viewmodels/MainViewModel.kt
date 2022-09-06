package com.example.colornotes.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.model.SqlRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _listNote = MutableLiveData<List<NoteData>>()
    private val _listColorGroup = MutableLiveData<List<ColorGroupData>>()
    val listDataNote: LiveData<List<NoteData>> = _listNote
    val listColorGroup: LiveData<List<ColorGroupData>> = _listColorGroup

    fun addNote(noteData: NoteData) = viewModelScope.launch {
        SqlRepository.createNoteData(noteData)
    }

    fun deleteNote(noteData: NoteData) = viewModelScope.launch {
        SqlRepository.deleteNoteData(noteData)
    }

    fun updateNote(noteData: NoteData) = viewModelScope.launch {
        SqlRepository.updateNoteData(noteData)
    }

    fun getListNote() = viewModelScope.launch {
        _listNote.postValue(SqlRepository.getListNoteData())
    }

    fun getListNote(vararg colorIdFilter: Int) = viewModelScope.launch {
        _listNote.postValue(SqlRepository.getListNoteData(colorIdFilter = colorIdFilter))
    }

    fun getListColorGroup() = viewModelScope.launch {
        _listColorGroup.postValue(SqlRepository.getListColorGroupData())
    }
}