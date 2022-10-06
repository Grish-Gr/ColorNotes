package com.example.colornotes.view.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

data class NoteData(
    val id: Long,
    var titleNote: String,
    var textNote: String,
    var createDate: Long,
    var colorGroup: ColorGroupData
): Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readParcelable(ColorGroupData::class.java.classLoader)!!
    ) {
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateByFormat(patternFormat: String = "dd/MM/yyyy"): String{
        val timeFormat = SimpleDateFormat(patternFormat)
        return timeFormat.format(createDate)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(titleNote)
        parcel.writeString(textNote)
        parcel.writeLong(createDate)
        parcel.writeParcelable(colorGroup, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteData> {
        override fun createFromParcel(parcel: Parcel): NoteData {
            return NoteData(parcel)
        }

        override fun newArray(size: Int): Array<NoteData?> {
            return arrayOfNulls(size)
        }
    }
}