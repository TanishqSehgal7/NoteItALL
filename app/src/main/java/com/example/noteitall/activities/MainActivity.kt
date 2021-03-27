package com.example.noteitall.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteitall.R
import com.example.noteitall.ViewModel.NoteViewModelClass
import com.example.noteitall.adapters.NotesRvAdapter
import com.example.noteitall.entities.Note
import com.example.noteitall.utility.CoRoutineUtilityClass
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : CoRoutineUtilityClass() {

    lateinit var viewModel : NoteViewModelClass
    lateinit var notesRecyclerView:RecyclerView
    private val ReqCodeForAddNote=1
    lateinit var note:Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN

        setContentView(R.layout.activity_main)

        notesRecyclerView=findViewById(R.id.NotesRV)
        notesRecyclerView.setHasFixedSize(true)
        notesRecyclerView.layoutManager=StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter=NotesRvAdapter(this)
        notesRecyclerView.adapter=adapter


        viewModel= ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        )
            .get(NoteViewModelClass::class.java)
        viewModel.allNotesLiveData.observe(this, { list ->
            list?.let {
                adapter.UpdateListAfterAnyChanges(it)
            }

        })

        val addNote:FloatingActionButton= findViewById(R.id.Fab_AddNote)
        addNote.setOnClickListener {
            val intent=Intent(this, NotesActivity::class.java)
//            startActivityForResult(intent, ReqCodeForAddNote)
            startActivity(intent)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode==ReqCodeForAddNote && resultCode== RESULT_OK){
//            val title: String = data?.getStringExtra(NotesActivity.EXTRA_TITLE)!!
//            val content: String = data.getStringExtra(NotesActivity.EXTRA_CONTENT)!!
//            note= Note(title, content)
//            viewModel.saveNote(note)
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()

        // close the primary database to ensure all the transactions are merged
    }

}