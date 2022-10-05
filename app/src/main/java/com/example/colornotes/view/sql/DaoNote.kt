package com.example.colornotes.view.sql

import androidx.room.*

@Dao
interface DaoNote {

    /**
     * @param sortFilter    SortFilter. Should be [TIME_EARLY], [TIME_OLDEST], [ALPHABET_A_Z] or [ALPHABET_Z_A].
     * @param colorId       IdColorGroup. If not specified, fun return all notes
     */
    @Query("SELECT * FROM notes WHERE color_id = :colorId ORDER BY " +
            "CASE WHEN :sortFilter = 0 THEN create_date END DESC," +
            "CASE WHEN :sortFilter = 1 THEN create_date END ASC," +
            "CASE WHEN :sortFilter = 2 THEN text_note END ASC," +
            "CASE WHEN :sortFilter = 3 THEN text_note END DESC")
    suspend fun getListNotes(sortFilter: Int, colorId: Long): List<Note>

    /**
     * @param sortFilter    SortFilter. Should be [TIME_EARLY], [TIME_OLDEST], [ALPHABET_A_Z] or [ALPHABET_Z_A].
     */
    @Query("SELECT * FROM notes " + "ORDER BY " +
            "CASE WHEN :sortFilter = 0 THEN create_date END DESC," +
            "CASE WHEN :sortFilter = 1 THEN create_date END ASC," +
            "CASE WHEN :sortFilter = 2 THEN text_note END ASC," +
            "CASE WHEN :sortFilter = 3 THEN text_note END DESC")
    suspend fun getListNotes(sortFilter: Int): List<Note>

    @Query("SELECT * FROM color_groups")
    suspend fun getColorGroups(): List<ColorGroup>

    @Query("SELECT * FROM color_groups WHERE id_color = :idColor")
    suspend fun getColorGroup(idColor: Long): ColorGroup

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    companion object{
        const val TIME_EARLY = 0
        const val TIME_OLDEST = 1
        const val ALPHABET_A_Z = 2
        const val ALPHABET_Z_A = 3
    }
}