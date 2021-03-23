package com.example.noteitall.Repository

import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.entities.Note
import androidx.lifecycle.LiveData

class NoteRepositoryClass(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> =noteDao.getAllNotes()

    suspend fun insertNewNote(note: Note){
        noteDao.insetNewNote(note)
    }

    suspend fun deleteExistingNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun AddMoreThanOneNote(note: Note){
        noteDao.addMoreThanOneNote(note)
    }

}