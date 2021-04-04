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

    @Insert
    suspend fun insetNewNote(note: Note)

    @Update
    suspend fun updateExistingNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Notes WHERE titleOFNote LIKE :searchQuery OR contentOFNote LIKE :searchQuery")
fun SearchNoteDatabase(searchQuery: String) : LiveData<List<Note>>

}