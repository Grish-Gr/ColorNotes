package com.example.colornotes.view.view.filter

sealed class SortFilter(val ordinal: Int, val title: String) {
    object TimeEarly:  SortFilter (0, "Time: Early")
    object TimeOldest: SortFilter(1,  "Time: Oldest")
    object AlphabetAZ: SortFilter(2, "Text: A - Z")
    object AlphabetZA: SortFilter(3, "Text: Z - A")

    companion object{
        val arraySortFilters: Array<SortFilter> by lazy {
            arrayOf(TimeEarly, TimeOldest, AlphabetAZ, AlphabetZA).clone()
        }
    }
}