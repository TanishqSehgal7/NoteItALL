package com.example.noteitall.activities

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteitall.R
import com.example.noteitall.ViewModel.NoteViewModelClass
import com.example.noteitall.entities.Note
import com.example.noteitall.utility.AlarmBroasCastReceiver
import com.example.noteitall.utility.CoRoutineUtilityClass
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

    lateinit var timePicker: MaterialTimePicker
    lateinit var calendar: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent


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
                finish()
            }

        }

        val backButton: ImageButton = findViewById(R.id.NotActivityExit)
        backButton.setOnClickListener {
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
                finish()
            }
        }

        CreateNotificationChannelForAlarm()

        SetNotificationForNote = findViewById(R.id.SetReminder)
        SetNotificationForNote.setOnClickListener {
            ShowTimePicker()

            SaveNoteButton.setOnClickListener {
                SetAlarm()
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
            note = Note(noteTitleText, noteContentText)
            note.TimeandDate = currentTimeandDate
            viewModel.insertNewNote(note)

            viewModel.UpdateNoteOnEdit(note)

            val data=Intent()
            data.putExtra(EXTRA_TITLE,noteTitleText)
            data.putExtra(EXTRA_CONTENT,noteContentText)
        }
        super.onBackPressed()
    }

    // in case app crashes while writing a note or while editing
    override fun onDestroy() {
        super.onDestroy()

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

            viewModel.UpdateNoteOnEdit(note)

            val data=Intent()
            data.putExtra(EXTRA_TITLE,noteTitleText)
            data.putExtra(EXTRA_CONTENT,noteContentText)
        }
    }

    fun CreateNotificationChannelForAlarm() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channelName: CharSequence = "NotificationForNote"
            val description = "Channel for the Note Notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel("alarmNotificationForNote", channelName, importance)
            notificationChannel.description = description

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun ShowTimePicker() {

        timePicker=MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12).setMinute(0).setTitleText("Select Time").build()

        timePicker.show(supportFragmentManager,"alarmNotificationForNote")
        timePicker.addOnPositiveButtonClickListener {

            calendar= Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY,timePicker.hour)
            calendar.set(Calendar.MINUTE,timePicker.minute)
            calendar.set(Calendar.SECOND,0)
            calendar.set(Calendar.MILLISECOND,0)
            val broadcastIntent = Intent(application, AlarmBroasCastReceiver::class.java).apply {
                putExtra("notificationTitle", noteTitle.text.toString())
                putExtra("notificationContent", noteContent.text.toString())
            }
            sendBroadcast(broadcastIntent)
        }
    }

    fun SetAlarm() {
        alarmManager=getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent=Intent(this,AlarmBroasCastReceiver::class.java)
        pendingIntent= PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        Toast.makeText(this,"Alarm Set Successfully",Toast.LENGTH_SHORT).show()
    }
    companion object {
        val EXTRA_TITLE: String = "com.example.noteitall.activities.EXTRA_TITLE"
        val EXTRA_CONTENT: String = "com.example.noteitall.activities.EXTRA_CONTENT"
        val EXTRA_NOTE_ID: String = "com.example.noteitall.activities.EXTRA_NOTE_ID"
    }
}