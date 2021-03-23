package com.example.noteitall.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="Notes")
data class Note(var titleOFNote:String, var contentOFNote:String): Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Int?=0

    @ColumnInfo(name = "date_Time")
    var TimeandDate:String?=null
}



