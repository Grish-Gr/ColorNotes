package com.example.colornotes.view.model

import android.os.Parcel
import android.os.Parcelable
import java.text.DateFormat
import java.util.*

data class NoteData(
    val id: Long,
    var titleNote: String,
    var textNote: String,
    var createDate: Long,
    var colorId: Long
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    fun getDate(): Date = Date(createDate)
    fun getDateByFormat(styleDateFormat: Int): String =
        DateFormat.getDateInstance(styleDateFormat).format(getDate())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(titleNote)
        parcel.writeString(textNote)
        parcel.writeLong(createDate)
        parcel.writeLong(colorId)
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