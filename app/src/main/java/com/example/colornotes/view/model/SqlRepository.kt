package com.example.colornotes.view.model

import com.example.colornotes.view.sql.DaoNote
import com.example.colornotes.view.sql.DatabaseNote
import com.example.colornotes.view.sql.Note
import com.example.colornotes.view.view.filter.SortFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SqlRepository {

    private lateinit var databaseNote: DatabaseNote
    private val dispatchersIO = Dispatchers.IO

    fun initRepository(database: DatabaseNote){
        databaseNote = database
    }

    suspend fun getListNoteData(sortFilter: SortFilter, colorIdFilter: Long): List<NoteData> = withContext(dispatchersIO){
        val list = when (sortFilter){
            SortFilter.TimeEarly -> databaseNote.getDao().getListNotes(DaoNote.TIME_EARLY, colorIdFilter)
            SortFilter.TimeOldest -> databaseNote.getDao().getListNotes(DaoNote.TIME_OLDEST, colorIdFilter)
            SortFilter.AlphabetAZ -> databaseNote.getDao().getListNotes(DaoNote.ALPHABET_A_Z, colorIdFilter)
            else -> databaseNote.getDao().getListNotes(DaoNote.ALPHABET_Z_A, colorIdFilter)
        }.map { note ->
            with(note) {
                val colorGroup = getColorGroupData(color_id)
                NoteData(id_note, title_note, text_note, create_note, colorGroup)
            }
        }
        return@withContext list
    }

    suspend fun getListNoteData(sortFilter: SortFilter) : List<NoteData> = withContext(dispatchersIO){
        val list = when (sortFilter){
            SortFilter.TimeEarly -> databaseNote.getDao().getListNotes(DaoNote.TIME_EARLY)
            SortFilter.TimeOldest -> databaseNote.getDao().getListNotes(DaoNote.TIME_OLDEST)
            SortFilter.AlphabetAZ -> databaseNote.getDao().getListNotes(DaoNote.ALPHABET_A_Z)
            else -> databaseNote.getDao().getListNotes(DaoNote.ALPHABET_Z_A)
        }.map { note ->
            with(note) {
                val colorGroup = getColorGroupData(color_id)
                NoteData(id_note, title_note, text_note, create_note, colorGroup)
            }
        }
        return@withContext list
    }

    suspend fun getListColorGroupData(): List<ColorGroupData> = withContext(dispatchersIO) {
        databaseNote.getDao().getColorGroups().map { it.getColorGroupData() }
    }

    suspend fun getColorGroupData(idColor: Long): ColorGroupData = withContext(dispatchersIO){
        //Log.e("TAG", "Request Sql get ColorGroup")
        databaseNote.getDao().getColorGroup(idColor = idColor).getColorGroupData()
    }

    suspend fun createNoteData(noteData: NoteData) = withContext(dispatchersIO){
        databaseNote.getDao().insertNote(Note.convertToNote(noteData))
    }

    suspend fun updateNoteData(noteData: NoteData) = withContext(dispatchersIO){
        databaseNote.getDao().updateNote(Note.convertToNote(noteData))
    }

    suspend fun deleteNoteData(noteData: NoteData) = withContext(dispatchersIO){
        databaseNote.getDao().deleteNote(Note.convertToNote(noteData))
    }
}