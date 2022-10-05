package com.example.colornotes.view.view.filter

import android.os.Parcel
import android.os.Parcelable

data class FilterSetting(
    var sortFilter: SortFilter,
    var viewFilter: ViewFilter,
    var filterGroup: Long? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        sortFilter = SortFilter.arraySortFilters[parcel.readInt()],
        viewFilter = ViewFilter.arrayViewFilters[parcel.readInt()],
        filterGroup = parcel.readValue(Long::class.java.classLoader) as? Long
    )

    constructor(ordinalSortFilter: Int, ordinalViewFilter: Int, filerGroup: Long?) : this (
        sortFilter = SortFilter.arraySortFilters[ordinalSortFilter],
        viewFilter = ViewFilter.arrayViewFilters[ordinalViewFilter],
        filterGroup = if (filerGroup == NO_FILTER_GROUP) null else filerGroup)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(sortFilter.ordinal)
        parcel.writeInt(viewFilter.ordinal)
        parcel.writeValue(filterGroup)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilterSetting> {
        const val NO_FILTER_GROUP = -1L

        override fun createFromParcel(parcel: Parcel): FilterSetting {
            return FilterSetting(parcel)
        }

        override fun newArray(size: Int): Array<FilterSetting?> {
            return arrayOfNulls(size)
        }

        fun getDefaultFilterSetting(): FilterSetting =
            FilterSetting(SortFilter.TimeEarly, ViewFilter.AllLineFilter, null)
    }
}