package com.example.colornotes.view.sql

import androidx.room.*
import kotlinx.coroutines.Job

@Dao
interface DaoNote {

    @Query("SELECT * FROM notes")
    suspend fun getListNotes(): List<Note>

    @Query("SELECT * FROM color_groups")
    suspend fun getColorGroups(): List<ColorGroup>

    @Query("SELECT * FROM notes WHERE color_id = :colorId")
    suspend fun getListNotesByColor(colorId: Long): List<Note>

    @Query("SELECT * FROM color_groups WHERE id_color = :idColor")
    suspend fun getColorGroup(idColor: Long): ColorGroup

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}