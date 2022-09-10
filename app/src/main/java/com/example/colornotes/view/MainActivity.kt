package com.example.colornotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.colornotes.R
import com.example.colornotes.databinding.ActivityMainBinding
import com.example.colornotes.view.model.SqlRepository
import com.example.colornotes.view.sql.DatabaseNote
import com.example.colornotes.view.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = Room.databaseBuilder(this, DatabaseNote::class.java, "note_database")
            .createFromAsset(DatabaseNote.pathToInitDatabase)
            .build()
        SqlRepository.initRepository(database)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container_fragment, MainFragment())
            .commit()
    }
}