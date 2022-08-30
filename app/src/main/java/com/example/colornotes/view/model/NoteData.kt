package com.example.colornotes.view.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class NoteData(
    val id: Long,
    var titleNote: String,
    var textNote: String,
    var createDate: Long,
    var colorId: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readLong(),
        parcel.readInt()
    ) {
    }

    fun getDate(): Date = Date(createDate)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(titleNote)
        parcel.writeString(textNote)
        parcel.writeLong(createDate)
        parcel.writeInt(colorId)
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