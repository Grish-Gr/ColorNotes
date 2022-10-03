package com.example.colornotes.view

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.colornotes.view.view.fragments.BaseFragment

class AppNote: Application() {

    override fun onCreate() {
        val sharedPreference = applicationContext.getSharedPreferences(
            BaseFragment.NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val isNightMode = sharedPreference.getBoolean(BaseFragment.KEY_EDIT_IS_NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES
            else             AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate()
    }
}