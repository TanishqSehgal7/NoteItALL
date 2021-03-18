package com.example.noteitall.activities.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.noteitall.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes ORDER BY id DESC")
    fun getAllNotes(): List<Note>

    @Insert
    fun insetAllNotes(note: Note)

    @Delete
    fun deleteNote(note: Note)

}