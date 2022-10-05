package com.example.colornotes.view.view.filter

import com.example.colornotes.R

sealed class ViewFilter(val ordinal: Int, val resIdIcon: Int){
    object LineFilter:    ViewFilter(0, R.drawable.ic_line_list_filter)
    object AllLineFilter: ViewFilter(1, R.drawable.ic_line_all_list_filter)
    object GridFilter:    ViewFilter(2, R.drawable.ic_grid_list_filter)

    companion object {
        val arrayViewFilters: Array<ViewFilter> by lazy {
            arrayOf(LineFilter, AllLineFilter, GridFilter).clone()
        }
    }
}