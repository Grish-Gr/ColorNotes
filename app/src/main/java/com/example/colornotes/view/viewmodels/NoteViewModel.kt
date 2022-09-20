package com.example.colornotes.view.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.model.SqlRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class NoteViewModel: ViewModel() {

    private var currentNoteData: NoteData? = null
    private val _listGroup = MutableLiveData<List<ColorGroupData>>()
    val listGroup: LiveData<List<ColorGroupData>> = _listGroup

    fun addNote(titleNote: String, textNote: String, idColorGroup: Long) = viewModelScope.launch {
        val colorGroup = getColorGroup(idColorGroup)
        SqlRepository.createNoteData(
            NoteData(
                0,
                titleNote,
                textNote,
                Calendar.getInstance().timeInMillis,
                colorGroup
            )
        )
        Log.e("TAG", "Finish Add")
    }

    fun updateNote(noteData: NoteData, idColorGroup: Long) = viewModelScope.launch{
        Log.e("TAG", "Current id: ${noteData.colorGroup.id}. Check id: $idColorGroup")
        val colorGroup = if (noteData.colorGroup.id != idColorGroup)
                             getColorGroup(idColorGroup)
                         else
                             noteData.colorGroup
        noteData.colorGroup = colorGroup
        noteData.createDate = Calendar.getInstance().timeInMillis
        SqlRepository.updateNoteData(noteData)
        Log.e("TAG", "Finish Update")
    }

    fun getListGroup() = viewModelScope.launch {
        _listGroup.postValue(SqlRepository.getListColorGroupData())
    }

    private suspend fun getColorGroup(idColorGroup: Long): ColorGroupData =
        withContext(viewModelScope.coroutineContext) {
            SqlRepository.getColorGroupData(idColorGroup)
        }
}
