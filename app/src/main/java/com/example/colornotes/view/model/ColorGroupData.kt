package com.example.colornotes.view.model

import android.graphics.Color
import android.os.Parcelable

data class ColorGroupData(
    val id: Long,
    val indexColor: String
){

    fun getColor(): Int = Color.parseColor(indexColor)

    fun getAlphaColor(): Int = Color.parseColor(indexAlpha + indexColor.drop(1))

    companion object{
        private const val indexAlpha = "#38"
    }
}