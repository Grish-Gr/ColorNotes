package com.example.colornotes.view.model

import android.util.Log
import com.example.colornotes.view.sql.DatabaseNote
import com.example.colornotes.view.sql.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

object SqlRepository {

    private lateinit var databaseNote: DatabaseNote
    private val dispatchersIO = Dispatchers.IO

    fun initRepository(database: DatabaseNote){
        databaseNote = database
    }

    suspend fun getListNoteData(): List<NoteData> = withContext(dispatchersIO){
        databaseNote.getDao().getListNotes().reversed().map { note ->
            //Log.e("TAG", "Create Note with request ColorGroup")
            val colorGroup = getColorGroupData(note.color_id)
            with(note){
                NoteData(
                    id_note,
                    title_note,
                    text_note,
                    create_note,
                    colorGroup
                )
            }
        }
    }

    suspend fun getListNoteData(colorIdFilter: Long) : List<NoteData> =
        withContext(dispatchersIO){
            databaseNote.getDao().getListNotesByColor(colorIdFilter).reversed().map { note ->
                val colorGroup = getColorGroupData(note.color_id)
                with(note){
                    NoteData(
                        id_note,
                        title_note,
                        text_note,
                        create_note,
                        colorGroup
                    )
                }
            }
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