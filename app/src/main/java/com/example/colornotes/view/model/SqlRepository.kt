package com.example.colornotes.view.model

import com.example.colornotes.view.sql.DatabaseNote
import com.example.colornotes.view.sql.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SqlRepository {

    private lateinit var databaseNote: DatabaseNote
    private val dispatchersIO = Dispatchers.IO

    fun initRepository(database: DatabaseNote){
        databaseNote = database
    }

    suspend fun getListNoteData(): List<NoteData> = withContext(dispatchersIO){
        databaseNote.getDao().getListNotes().map { it.getNoteData() }
    }

    suspend fun getListNoteData(vararg colorIdFilter: Int) : List<NoteData> =
        withContext(dispatchersIO){
            databaseNote.getDao().getListNotesByColor()
                .map { it.getNoteData() }
        }

    suspend fun getListColorGroupData(): List<ColorGroupData> = withContext(dispatchersIO) {
        databaseNote.getDao().getColorGroups().map { it.getColorGroupData() }
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