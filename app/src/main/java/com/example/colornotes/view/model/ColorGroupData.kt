package com.example.colornotes.view.model

import android.graphics.Color

data class ColorGroupData(
    val id: Int,
    val indexColor: String
){

    fun getColor(): Int = Color.parseColor(indexColor)

    fun getAlphaColor(): Int = Color.parseColor(indexAlpha + indexColor)

    companion object{
        private const val indexAlpha = "38"
    }
}