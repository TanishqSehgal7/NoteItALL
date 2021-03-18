package com.example.noteitall.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Notes")
data class Note (

    @PrimaryKey(autoGenerate = true)
    val id:Int?,

    @ColumnInfo(name="Title")
    val titleOfNote:String,

    @ColumnInfo(name = "date_Time")
    val dateTime:String,

    @ColumnInfo(name = "ContentOfNote")
    val contentOfNote:String,

    @ColumnInfo(name = "ImagePath")
    val imagePath:String,

    @ColumnInfo(name= "Color")
    val color:String,

    @ColumnInfo(name = "WebLink")
    val webLik:String,

        )



