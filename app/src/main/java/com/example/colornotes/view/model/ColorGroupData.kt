package com.example.colornotes.view.model

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class ColorGroupData(
    var id: Long,
    var indexColor: String
): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString()
    )

    fun getColor(): Int = Color.parseColor(indexColor)
    fun getAlphaColor(): Int = Color.parseColor(indexAlpha + indexColor.drop(1))

    fun getColorState(): ColorStateList = ColorStateList.valueOf(getColor())
    fun getAlphaColorState(): ColorStateList = ColorStateList.valueOf(getAlphaColor())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(indexColor)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ColorGroupData> {
        private const val indexAlpha = "#38"
        override fun createFromParcel(parcel: Parcel): ColorGroupData {
            return ColorGroupData(parcel)
        }

        override fun newArray(size: Int): Array<ColorGroupData?> {
            return arrayOfNulls(size)
        }
    }

}