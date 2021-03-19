package com.example.noteitall.DAO

import androidx.room.*
import com.example.noteitall.entities.Note

@Dao
interface NoteDao {

    //suspend functions are always called inside a coroutine scope

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}