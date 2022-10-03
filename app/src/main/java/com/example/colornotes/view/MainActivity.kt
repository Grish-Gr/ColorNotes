package com.example.colornotes.view

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.example.colornotes.R
import com.example.colornotes.databinding.ActivityMainBinding
import com.example.colornotes.view.model.SqlRepository
import com.example.colornotes.view.sql.DatabaseNote
import com.example.colornotes.view.view.fragments.BaseFragment
import com.example.colornotes.view.view.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //initTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val database = Room.databaseBuilder(this, DatabaseNote::class.java, "note_database")
            .createFromAsset(DatabaseNote.pathToInitDatabase)
            .build()
        SqlRepository.initRepository(database)

        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container_fragment, MainFragment())
                .commit()
        }
    }

    /*private fun initTheme(){
        val sharedPreference = getSharedPreferences(BaseFragment.NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val isNightMode = sharedPreference.getBoolean(BaseFragment.KEY_EDIT_IS_NIGHT_MODE, false)
        Log.e("TAG", "Is Night Mode - $isNightMode")
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES
            else             AppCompatDelegate.MODE_NIGHT_NO)
    }*/
}