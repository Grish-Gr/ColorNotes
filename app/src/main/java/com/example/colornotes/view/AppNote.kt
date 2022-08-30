package com.example.colornotes.view

import android.app.Application
import androidx.room.Room
import com.example.colornotes.view.model.SqlRepository
import com.example.colornotes.view.sql.DatabaseNote

class AppNote: Application() {
    init {
        val database = Room.databaseBuilder(this, DatabaseNote::class.java, "note_database")
            .createFromAsset(DatabaseNote.pathToInitDatabase)
            .build()
        SqlRepository.initRepository(database)
    }
}