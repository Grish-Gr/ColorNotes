package com.example.colornotes.view.sql

import androidx.room.*

@Dao
interface DaoNote {

    @Query("SELECT * FROM notes")
    suspend fun getListNotes(): List<Note>

    @Query("SELECT * FROM color_groups")
    suspend fun getColorGroups(): List<ColorGroup>

    @Query("SELECT * FROM notes WHERE color_id IN (:colorIdFilter)")
    suspend fun getListNotesByColor(vararg colorIdFilter: Int): List<Note>

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}