package com.example.noteitall.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.entities.Note
import androidx.room.Room.databaseBuilder

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class NotesDataBase: RoomDatabase() {
//    abstract val noteDao: NoteDao

    abstract fun GetNoteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDataBase? = null

        fun MakeTheDataBase(context: Context) : NotesDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDataBase::class.java,
                    "Notes"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }

        }


    }

}