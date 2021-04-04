package com.example.noteitall.Repository

import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.entities.Note
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
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

    fun SearchNoteDatabase(searchQuery: String): LiveData<List<Note>>{
        return noteDao.SearchNoteDatabase(searchQuery)
    }

}