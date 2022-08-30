package com.example.colornotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colornotes.R
import com.example.colornotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}