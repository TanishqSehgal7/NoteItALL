package com.example.noteitall.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.noteitall.DataBase.NotesDataBase
import com.example.noteitall.R
import com.example.noteitall.ViewModel.NoteViewModelClass
import com.example.noteitall.entities.Note
import com.example.noteitall.utility.CoRoutineUtilityClass
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
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
    private lateinit var SetNotificationForNote:ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN
        setContentView(R.layout.activity_notes)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(NoteViewModelClass::class.java)

        val backButton: ImageButton = findViewById(R.id.NotActivityExit)
        backButton.setOnClickListener {
            finish()
        }

        noteTitle = findViewById(R.id.Title_ET)
        noteContent = findViewById(R.id.NoteContent)
        dateandtime = findViewById(R.id.DateAndTimeTV)

        val sdf = SimpleDateFormat("dd/M/yyyy @ hh:mm:ss")
        currentTimeandDate = sdf.format(Date())
        dateandtime.text = currentTimeandDate

        val SaveNoteButton: ImageButton = findViewById(R.id.SaveNote)
        val replyIntent = Intent()

        if (intent.hasExtra(EXTRA_NOTE_ID)) {
            noteTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            noteContent.setText(intent.getStringExtra(EXTRA_CONTENT))
        }


        SaveNoteButton.setOnClickListener {
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
                note = Note(noteTitleText, noteContentText)
                note.TimeandDate = currentTimeandDate
                replyIntent.putExtra(EXTRA_TITLE, note.titleOFNote)
                replyIntent.putExtra(EXTRA_CONTENT, note.contentOFNote)
                var id: Int = intent.getIntExtra(EXTRA_NOTE_ID, -1)
                id = note.id
                if (id != -1) {
                    replyIntent.putExtra(EXTRA_NOTE_ID, id)
                    viewModel.UpdateNoteOnEdit(note)
                }
                viewModel.insertNewNote(note)
//                replyIntent.putExtra(EXTRA_NOTE_ID,note.id)
//                    viewModel.insertNewNote(note)

//                replyIntent.putExtra(EXTRA_TITLE,note.titleOFNote)
//                replyIntent.putExtra(EXTRA_CONTENT,note.contentOFNote)

//                val id:Int=intent.getIntExtra(EXTRA_NOTE_ID,-1)
//                if (id!=-1){
//                    replyIntent.putExtra(EXTRA_NOTE_ID,id)
//                    viewModel.UpdateNoteOnEdit(note)
//                }
//                setResult(RESULT_OK, replyIntent)
                finish()
            }
        }


        SetNotificationForNote = findViewById(R.id.SetReminder)
        SetNotificationForNote.setOnClickListener {
//            val intent=Intent(this,SetReminderWithDateAndTimePicker::class.java)
//            this.startActivity(intent)
            val dateFormat=SimpleDateFormat("dd MMM YYYY",Locale.US)
            val timeFormat=SimpleDateFormat("hh mm a", Locale.US)
            val calendar=Calendar.getInstance()
            val dateForReminder=Calendar.getInstance()
            val timeForReminder=Calendar.getInstance()
            val datePickerDialog= DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, month, day ->
                dateForReminder.set(Calendar.YEAR,year)
                dateForReminder.set(Calendar.MONTH,month)
                dateForReminder.set(Calendar.DAY_OF_MONTH,day)
                val SelectedDate=dateFormat.format(dateForReminder.time)
                Toast.makeText(this,SelectedDate,Toast.LENGTH_SHORT).show()

                val timePickerDialog=TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
                        view, hours, minutes ->
                    timeForReminder.set(Calendar.HOUR_OF_DAY,hours)
                    timeForReminder.set(Calendar.MINUTE,minutes)
                    val selectedTime=timeFormat.format(timeForReminder.time)
                    Toast.makeText(this,selectedTime,Toast.LENGTH_SHORT).show()
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                    false)
                timePickerDialog.show()

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()

//            val timePickerDialog=TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {
//                    view, hours, minutes ->
//                timeForReminder.set(Calendar.HOUR_OF_DAY,hours)
//                timeForReminder.set(Calendar.MINUTE,minutes)
//                val selectedTime=timeFormat.format(timeForReminder.time)
//                Toast.makeText(this,selectedTime,Toast.LENGTH_SHORT).show()
//            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
//                false)
//            timePickerDialog.show()

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
            note = Note(noteTitleText, noteContentText)
            note.TimeandDate = currentTimeandDate
            viewModel.insertNewNote(note)

            if (note!=null){
                viewModel.UpdateNoteOnEdit(note)
            }

            val data=Intent()
            data.putExtra(EXTRA_TITLE,noteTitleText)
            data.putExtra(EXTRA_CONTENT,noteContentText)

//            val id:Int=intent.getIntExtra(EXTRA_NOTE_ID,-1)
//            if (id!=-1){
//                data.putExtra(EXTRA_NOTE_ID,id)
//            }
//            setResult(RESULT_OK, data)
        }
        super.onBackPressed()
    }

    companion object {
        val EXTRA_TITLE: String = "com.example.noteitall.activities.EXTRA_TITLE"
        val EXTRA_CONTENT: String = "com.example.noteitall.activities.EXTRA_CONTENT"
        val EXTRA_NOTE_ID: String = "com.example.noteitall.activities.EXTRA_NOTE_ID"
    }
}