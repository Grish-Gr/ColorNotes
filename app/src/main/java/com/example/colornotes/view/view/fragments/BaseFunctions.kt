package com.example.colornotes.view.view.fragments

import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.colornotes.R
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.view.filter.ViewFilter
import com.google.android.material.chip.Chip


fun Fragment.getColorGroupChip(colorGroupData: ColorGroupData): Chip {
    val chip = Chip(this.context)
    val widthChip = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics).toInt()
    with (chip) {
        id = colorGroupData.id.toInt()
        setTag(R.string.tag_chip_color_group, colorGroupData)
        chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_circle)
        chipIconTint = colorGroupData.getColorState()
        chipBackgroundColor = colorGroupData.getAlphaColorState()
        width = widthChip
        isCheckable = true
    }
    return chip
}

fun Fragment.getViewFilterRadioButton(viewFilter: ViewFilter): RadioButton {
    val radioButton = RadioButton(this.context)
    with(radioButton){
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 32, 0)
        layoutParams = params
        id = viewFilter.ordinal
        setTag(R.string.tag_button_view_filter, viewFilter)
        setCompoundDrawablesWithIntrinsicBounds(0, 0, viewFilter.resIdIcon, 0)
    }
    return radioButton
}