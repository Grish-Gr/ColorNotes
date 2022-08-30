package com.example.colornotes.view.model

import com.example.colornotes.view.sql.DatabaseNote
import com.example.colornotes.view.sql.Note

object SqlRepository {

    private lateinit var databaseNote: DatabaseNote

    fun initRepository(database: DatabaseNote){
        databaseNote = database
    }

    suspend fun getListNoteData(): List<NoteData> =
        databaseNote.getDao().getListNotes().map { it.getNoteData() }

    suspend fun getListNoteData(vararg colorIdFilter: Int) : List<NoteData> =
        databaseNote.getDao().getListNotesByColor(colorIdFilter = colorIdFilter)
            .map { it.getNoteData() }

    suspend fun getListColorGroupData(): List<ColorGroupData> =
        databaseNote.getDao().getColorGroups().map { it.getColorGroupData() }

    suspend fun createNoteData(noteData: NoteData) =
        databaseNote.getDao().insertNote(Note.convertToNote(noteData))

    suspend fun updateNoteData(noteData: NoteData) =
        databaseNote.getDao().updateNote(Note.convertToNote(noteData))


    suspend fun deleteNoteData(noteData: NoteData) =
        databaseNote.getDao().deleteNote(Note.convertToNote(noteData))
}