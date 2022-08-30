package com.example.colornotes.view.sql

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.colornotes.view.model.ColorGroupData

@Entity(tableName = "color_groups")
data class ColorGroup(

    @ColumnInfo(name = "id_color")
    @PrimaryKey(autoGenerate = true)
    val id_color: Int,

    @ColumnInfo(name = "index_color")
    val index_color: String
){
    fun getColorGroupData(): ColorGroupData =
        ColorGroupData(id_color, index_color)

    companion object{
        fun convertToColorGroup(colorGroupData: ColorGroupData): ColorGroup =
            with(colorGroupData){
                ColorGroup(id, indexColor)
            }
    }
}