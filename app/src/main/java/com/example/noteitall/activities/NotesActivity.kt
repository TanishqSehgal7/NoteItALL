package com.example.noteitall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.noteitall.DataBase.NotesDataBase
import com.example.noteitall.R
import com.example.noteitall.entities.Note
import com.example.noteitall.utility.CoRoutineUtilityClass
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotesActivity : CoRoutineUtilityClass() {

    private lateinit var noteTitle:EditText
    private lateinit var noteContent:EditText
    private lateinit var dateandtime:TextView
    private lateinit var currentTimeandDate:String
    private var IDofTheNote=-1
    var colorChosen= "#FFBB00"
    private var WebUrl=""
    private var PathOfImage=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN


        setContentView(R.layout.activity_notes)
        val backButton:ImageButton=findViewById(R.id.NotActivityExit)
        backButton.setOnClickListener {
           finish()
        }

        noteTitle=findViewById(R.id.Title_ET)
        noteContent=findViewById(R.id.NoteContent)
        dateandtime=findViewById(R.id.DateAndTimeTV)

        val sdf=SimpleDateFormat("dd/M/yyyy @ hh:mm:ss")
        currentTimeandDate=sdf.format(Date())
        dateandtime.text=currentTimeandDate

        val SaveNoteButton:ImageButton=findViewById(R.id.SaveNote)
        val note = Note()

        SaveNoteButton.setOnClickListener {

            if (noteTitle.text.toString().trim().isEmpty()){
                noteTitle.requestFocus()
                Toast.makeText(this,"Please enter the note title",Toast.LENGTH_SHORT).show()

            } else if(noteContent.text.toString().trim().isEmpty()) {
                noteContent.requestFocus()
                Toast.makeText(this,"Note Content cannot be empty",Toast.LENGTH_SHORT).show()

            } else {

                launch {

                    note.titleOfNote = noteTitle.text.toString()
                    note.contentOfNote = noteContent.text.toString()
                    note.TimeandDate = currentTimeandDate
                    note.id = IDofTheNote
                    note.color=colorChosen
                    note.link=WebUrl
                    note.imagePath=PathOfImage

                    applicationContext.let {

                        NotesDataBase(it)?.noteDao()?.insetNote(note)
//                    noteTitle.setText("")
//                    noteContent.setText("")

                    }
                }

                Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }
}