package com.example.noteitall.Repository

import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.entities.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

class NoteRepositoryClass(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> =noteDao.getAllNotes()

    suspend fun insertNewNote(note: Note){
        noteDao.insetNewNote(note)
    }

    suspend fun deleteExistingNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun UpdateNoteOnEdit(note: Note){
        noteDao.updateExistingNote(note)
    }


}