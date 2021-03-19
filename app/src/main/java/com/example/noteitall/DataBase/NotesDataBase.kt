package com.example.noteitall.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.entities.Note

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class NotesDataBase: RoomDatabase() {
//    abstract val noteDao: NoteDao

    abstract fun noteDao(): NoteDao

    companion object {

        private var instance:NotesDataBase?=null
        private val LOCK=Any()

        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?.DataBaseBuild(context).also {
                instance=it
            }
        }

    }
    private fun DataBaseBuild(context: Context) = Room.databaseBuilder(
        context.applicationContext, NotesDataBase::class.java, "NoteDataBase"
    ).build()
}