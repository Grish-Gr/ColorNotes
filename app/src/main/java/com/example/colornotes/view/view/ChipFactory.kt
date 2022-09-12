package com.example.colornotes.view.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.example.colornotes.R
import com.example.colornotes.view.model.ColorGroupData
import com.google.android.material.chip.Chip

object ChipFactory {

    fun getChip(context: Context, colorGroupData: ColorGroupData): Chip{
        val chip = Chip(context)
        with (chip) {
            id = colorGroupData.id.toInt()
            tag = colorGroupData.id
            chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_circle)
            chipIconTint = colorGroupData.getColorState()
            chipBackgroundColor = colorGroupData.getAlphaColorState()
            width = defaultWidthChip
            isCheckable = true
            Log.e("TAG", tag.toString())
        }
        return chip
    }

    private val defaultWidthChip = 100
        get() = (field * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    const val DefaultId = 0
}