package com.example.colornotes.view.view.filter

import android.os.Parcel
import android.os.Parcelable

data class FilterSetting(
    val filterSorting: Int,
    val filterView:    Int,
    val filterGroup:   Long? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(filterSorting)
        parcel.writeInt(filterView)
        parcel.writeValue(filterGroup)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilterSetting> {
        override fun createFromParcel(parcel: Parcel): FilterSetting {
            return FilterSetting(parcel)
        }

        override fun newArray(size: Int): Array<FilterSetting?> {
            return arrayOfNulls(size)
        }
    }
}