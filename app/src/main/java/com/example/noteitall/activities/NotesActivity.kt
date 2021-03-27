package com.example.noteitall.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteitall.DataBase.NotesDataBase
import com.example.noteitall.R
import com.example.noteitall.ViewModel.NoteViewModelClass
import com.example.noteitall.entities.Note
import com.example.noteitall.utility.CoRoutineUtilityClass
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotesActivity() : CoRoutineUtilityClass() {

    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText
    private lateinit var dateandtime: TextView
    private lateinit var currentTimeandDate: String
    private lateinit var viewModel: NoteViewModelClass
    lateinit var note: Note
    private lateinit var noteTitleText: String
    private lateinit var noteContentText: String
    private var notesList = ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        setContentView(R.layout.activity_notes)

        viewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModelClass::class.java)

        val backButton: ImageButton = findViewById(R.id.NotActivityExit)
        backButton.setOnClickListener {
            finish()
        }

        noteTitle=findViewById(R.id.Title_ET)
        noteContent=findViewById(R.id.NoteContent)
        dateandtime=findViewById(R.id.DateAndTimeTV)

        val sdf = SimpleDateFormat("dd/M/yyyy @ hh:mm:ss")
        currentTimeandDate = sdf.format(Date())
        dateandtime.text = currentTimeandDate

        val SaveNoteButton: ImageButton = findViewById(R.id.SaveNote)

        SaveNoteButton.setOnClickListener {
            val replyIntent = Intent()
            if (noteTitle.text.toString().trim().isEmpty()) {
                noteTitle.requestFocus()
                noteTitle.error = "Title required!"
                Toast.makeText(this, "Please enter the note title", Toast.LENGTH_SHORT).show()

            } else if (noteContent.text.toString().trim().isEmpty()) {
                noteContent.requestFocus()
                noteTitle.error = "Note body required!"
                Toast.makeText(this, "Note Content cannot be empty", Toast.LENGTH_SHORT).show()

            } else {
                noteTitleText = noteTitle.text.toString()
                noteContentText = noteContent.text.toString()

                viewModel.allNotesLiveData.observe(this, androidx.lifecycle.Observer { list ->

                })
                val note = Note(noteTitleText, noteContentText)
                note.TimeandDate = currentTimeandDate
                viewModel.insertNewNote(note)
//                replyIntent.putExtra(EXTRA_TITLE,noteTitleText)
//                replyIntent.putExtra(EXTRA_CONTENT,noteContentText)
//                setResult(Activity.RESULT_OK,replyIntent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (noteTitle.text.toString().trim().isEmpty() or noteContent.text.toString().trim().isEmpty()) {
            finish()

        } else {
            noteTitleText = noteTitle.text.toString()
            noteContentText = noteContent.text.toString()
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            )
                .get(NoteViewModelClass::class.java)
            viewModel.allNotesLiveData.observe(this, { list ->

            })
            val note = Note(noteTitleText, noteContentText)
            note.TimeandDate = currentTimeandDate
            viewModel.insertNewNote(note)
//        replyIntent.putExtra(EXTRA_TITLE,noteTitleText)
//        replyIntent.putExtra(EXTRA_CONTENT,noteContentText)
//        setResult(Activity.RESULT_OK,replyIntent)
        }
        super.onBackPressed()
    }

    companion object {
        val EXTRA_TITLE: String = "com.example.noteitall.activities.EXTRA_TITLE"
        val EXTRA_CONTENT: String = "com.example.noteitall.activities.EXTRA_CONTENT"
    }

}