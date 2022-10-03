package com.example.colornotes.view.view.filter

import com.example.colornotes.R

sealed class ViewFilter(val ordinal: Int, val idButton: Int) {
    class LineFilter(): ViewFilter(0, R.id.line_list_filter)
    class AllLineFilter: ViewFilter(1, R.id.line_list_all_filter)
    class GridFilter: ViewFilter(2, R.id.grid_list_filter)

    companion object {

        fun getViewFilter(idButton: Int): ViewFilter = when(idButton){
            R.id.line_list_filter -> LineFilter()
            R.id.line_list_all_filter -> AllLineFilter()
            else -> GridFilter()
        }

        fun getViewFilterByOrdinal(ordinal: Int): ViewFilter = when(ordinal){
            0 -> LineFilter()
            1 -> AllLineFilter()
            else -> GridFilter()
        }
    }
}