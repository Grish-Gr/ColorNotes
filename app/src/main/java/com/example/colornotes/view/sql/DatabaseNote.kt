package com.example.colornotes.view.sql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class, ColorGroup::class], version = 1)
abstract class DatabaseNote: RoomDatabase() {
    abstract fun getDao(): DaoNote

    companion object{
        const val pathToInitDatabase = "assets/init_note_database"
    }
}