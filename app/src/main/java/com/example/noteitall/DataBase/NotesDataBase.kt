package com.example.noteitall.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.entities.Note

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class NotesDataBase: RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object {

        private val INSTANCE:NotesDataBase?=null

        fun getInstance(context: Context) : NotesDataBase? {
            var instance= INSTANCE

            if (instance!=null){
                instance= Room.databaseBuilder(context.applicationContext,
                NotesDataBase::class.java,
                "NotesDataBase")
                    .fallbackToDestructiveMigration()
                    .build()
                instance= INSTANCE
            }
            return instance
        }
    }

    abstract fun noteDao(): NoteDao

}