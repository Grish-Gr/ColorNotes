package com.example.colornotes.view.adapters

import com.example.colornotes.R

//TODO change to sealed class
sealed class TypeHolder(val ordinal: Int, val resLayout: Int){
    object TypeItemLine:     TypeHolder(0, R.layout.item_line)
    object TypeItemAllLine:  TypeHolder(1, R.layout.item_all_line)
    object TypeItemGrid:     TypeHolder(2, R.layout.item_grid)
}