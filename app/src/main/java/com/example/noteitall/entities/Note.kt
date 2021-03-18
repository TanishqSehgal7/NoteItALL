package com.example.noteitall.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="Notes")
class Note: Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Int?=null

    @ColumnInfo(name="Title")
    var titleOfNote:String?= null

    @ColumnInfo(name = "date_Time")
    var TimeandDate:String?=null

    @ColumnInfo(name = "ContentOfNote")
    var contentOfNote:String?=null

    @ColumnInfo(name = "ImagePath")
    var imagePath:String?=null

    @ColumnInfo(name= "Color")
    var color:String?=null

    @ColumnInfo(name = "Link")
    var link:String?=null

    @Override
    override fun toString():String{
        return "$titleOfNote : $TimeandDate"
    }
}



