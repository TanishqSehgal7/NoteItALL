package com.example.noteitall.ViewModel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.noteitall.DAO.NoteDao
import com.example.noteitall.DataBase.NotesDataBase
import com.example.noteitall.Repository.NoteRepositoryClass
import com.example.noteitall.activities.MainActivity
import com.example.noteitall.entities.Note
import com.example.noteitall.utility.CoRoutineUtilityClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModelClass(application:Application) : AndroidViewModel(application) {

    val allNotesLiveData: LiveData<List<Note>>
    private val noteRepositoryClass:NoteRepositoryClass

    init {
        val noteDatabseDao=NotesDataBase.MakeTheDataBase(application).GetNoteDao()
        noteRepositoryClass=NoteRepositoryClass(noteDatabseDao)
        allNotesLiveData= noteRepositoryClass.allNotes
    }

    fun insertNewNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepositoryClass.insertNewNote(note)
    }

    fun DeleteNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        noteRepositoryClass.deleteExistingNote(note)
    }

    fun UpdateNoteOnEdit(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        noteRepositoryClass.UpdateNoteOnEdit(note)
    }
}