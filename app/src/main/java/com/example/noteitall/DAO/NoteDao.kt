package com.example.noteitall.DAO

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.noteitall.entities.Note

@Dao
interface NoteDao {

    //suspend functions are always called inside a coroutine scope

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetNewNote(vararg note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoreThanOneNote(vararg note: Note)

    @Update
    suspend fun updateExistingNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}