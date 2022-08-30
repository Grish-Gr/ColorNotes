package com.example.colornotes.view.model

import android.graphics.Color

data class ColorGroupData(
    val id: Int,
    val indexColor: String
){

    fun getColor(): Int = Color.parseColor(indexColor)
}