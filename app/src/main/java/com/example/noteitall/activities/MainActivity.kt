package com.example.noteitall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteitall.DataBase.NotesDataBase
import com.example.noteitall.R
import com.example.noteitall.adapters.NotesRvAdapter
import com.example.noteitall.utility.CoRoutineUtilityClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : CoRoutineUtilityClass() {

    lateinit var notesRecyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN

        setContentView(R.layout.activity_main)

        val addNote:FloatingActionButton= findViewById(R.id.Fab_AddNote)
        addNote.setOnClickListener {
            val intent=Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }

        notesRecyclerView=findViewById(R.id.NotesRV)
        notesRecyclerView.setHasFixedSize(true)
        notesRecyclerView.layoutManager=StaggeredGridLayoutManager(
            2,StaggeredGridLayoutManager.VERTICAL)


        launch {
            applicationContext.let {
                val notes= NotesDataBase(this@MainActivity)?.noteDao()?.getAllNotes()
                notesRecyclerView.adapter= notes?.let { NotesRvAdapter(it) }
            }

        }


    }
}