package com.example.colornotes.view.sql

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.colornotes.view.model.NoteData

@Entity(
    tableName = "notes",
    indices = [Index(value = ["color_id"])],
    foreignKeys = [ForeignKey(
        entity = ColorGroup::class,
        parentColumns = ["id_color"],
        childColumns = ["color_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )])
data class Note(

    @ColumnInfo(name = "id_note")
    @PrimaryKey(autoGenerate = true)
    val id_note: Long,

    @ColumnInfo(name= "title_note")
    var title_note: String,

    @ColumnInfo(name = "text_note")
    var text_note: String,

    @ColumnInfo(name = "create_date")
    var create_note: Long,

    @ColumnInfo(name = "color_id")
    var color_id: Long
){

    companion object{
        fun convertToNote(noteData: NoteData): Note = with(noteData){
            return Note(id,
                titleNote,
                textNote,
                createDate,
                noteData.colorGroup.id
            )
        }
    }
}