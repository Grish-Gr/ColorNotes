package com.example.colornotes.view.viewmodels

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.appcompat.content.res.AppCompatResources
import com.example.colornotes.R
import com.example.colornotes.view.model.ColorGroupData
import com.google.android.material.chip.Chip

object ChipFactory {

    fun getFilterChip(context: Context, colorGroupData: ColorGroupData): Chip{
        val chip = Chip(context)
        with (chip) {
            chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_circle)
            chipIconTint = ColorStateList.valueOf(colorGroupData.getColor())
            chipBackgroundColor = ColorStateList.valueOf(colorGroupData.getAlphaColor())
            width = toDP(100)
        }
        return chip
    }

    private fun toDP(value: Int) =
        (value * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}