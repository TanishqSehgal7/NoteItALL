package com.example.noteitall.activities

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
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
import org.w3c.dom.Text
import java.text.FieldPosition

class MainActivity : CoRoutineUtilityClass(), NotesRvAdapter.NoteItemClickListener,
    NotesRvAdapter.DeleteNoteOnLongClickListener {

    lateinit var viewModel: NoteViewModelClass
    lateinit var notesRecyclerView: RecyclerView
    lateinit var deleteNoteBTN: ImageButton
    lateinit var ClosedeleteNoteSelectionBTN: ImageButton
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var note: Note
    private val ADD_NOTE_REQ = 1
    private val EDIT_NOTE_REQ = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        WindowManager.LayoutParams.FLAG_FULLSCREEN

        setContentView(R.layout.activity_main)
        deleteNoteBTN = findViewById(R.id.DeleteNote)
        searchView = findViewById(R.id.search_btn)
        ClosedeleteNoteSelectionBTN = findViewById(R.id.CloseDeleteNote)
        deleteNoteBTN.visibility = View.INVISIBLE
        ClosedeleteNoteSelectionBTN.visibility = View.GONE

        notesRecyclerView = findViewById(R.id.NotesRV)
        notesRecyclerView.setHasFixedSize(true)
        notesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter = NotesRvAdapter(this, this, this)
        notesRecyclerView.adapter = adapter


        viewModel = ViewModelProvider(
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

        val addNote: FloatingActionButton = findViewById(R.id.Fab_AddNote)
        addNote.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQ)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode==ADD_NOTE_REQ && resultCode== RESULT_OK){
//            val title: String = data?.getStringExtra(NotesActivity.EXTRA_TITLE)!!
//            val content: String = data.getStringExtra(NotesActivity.EXTRA_CONTENT)!!
//            note= Note(title, content)
//            viewModel.insertNewNote(note)
//        } else if (requestCode==EDIT_NOTE_REQ && resultCode== RESULT_OK){
//            val id:Int= data!!.getIntExtra(NotesActivity.EXTRA_NOTE_ID,-1)
//            if (id==-1){
//                Toast.makeText(this,"Cannot Update the Note",Toast.LENGTH_SHORT).show()
//                return
//            } else {
//                val title: String = data?.getStringExtra(NotesActivity.EXTRA_TITLE)!!
//                val content: String = data.getStringExtra(NotesActivity.EXTRA_CONTENT)!!
//                note= Note(title, content)
//                note.id=id
//                viewModel.UpdateNoteOnEdit(note)
//                Toast.makeText(this,"Note Updated",Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    override fun onBackPressed() {
        if (deleteNoteBTN.visibility == View.VISIBLE && ClosedeleteNoteSelectionBTN.visibility == View.VISIBLE && searchView.visibility == View.GONE) {
            deleteNoteBTN.visibility = View.GONE
            ClosedeleteNoteSelectionBTN.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }
    }

    override fun OnNoteClickListener(note: Note) {
//        val intent=Intent(this,NotesActivity::class.java).apply {
//            intent.putExtra(NotesActivity.EXTRA_NOTE_ID, note.id)
//            intent.putExtra(NotesActivity.EXTRA_TITLE, note.titleOFNote)
//            intent.putExtra(NotesActivity.EXTRA_CONTENT, note.contentOFNote)
//        }
//            startActivityForResult(intent, EDIT_NOTE_REQ)
        val intent = Intent(this, NotesActivity::class.java)

        deleteNoteBTN.visibility = View.GONE
        ClosedeleteNoteSelectionBTN.visibility=View.GONE
        searchView.visibility=View.VISIBLE

        intent.putExtra(NotesActivity.EXTRA_NOTE_ID,note.id)
        intent.putExtra(NotesActivity.EXTRA_TITLE,note.titleOFNote)
        intent.putExtra(NotesActivity.EXTRA_CONTENT,note.titleOFNote)
        startActivity(intent)
    }

    override fun DeleteNoteOnLongClick(note: Note) {
        deleteNoteBTN.visibility = View.VISIBLE
        ClosedeleteNoteSelectionBTN.visibility = View.VISIBLE
        searchView.visibility = View.GONE
        deleteNoteBTN.setOnClickListener {
            viewModel.DeleteNote(note)
            ClosedeleteNoteSelectionBTN.visibility = View.GONE
            deleteNoteBTN.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

        ClosedeleteNoteSelectionBTN.setOnClickListener {
            deleteNoteBTN.visibility = View.GONE
            ClosedeleteNoteSelectionBTN.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==ADD_NOTE_REQ && resultCode== RESULT_OK) {
            val title: String = data?.getStringExtra(NotesActivity.EXTRA_TITLE).toString()
            val content:String= data?.getStringExtra(NotesActivity.EXTRA_CONTENT).toString()
            note=Note(title,content)
            viewModel.insertNewNote(note)
        } else if (requestCode==EDIT_NOTE_REQ && resultCode== RESULT_OK) {
            val id:Int = data!!.getIntExtra(NotesActivity.EXTRA_NOTE_ID,-1)
            if (id==-1){
                Toast.makeText(this,"Cannot Update Note",Toast.LENGTH_SHORT).show()
            }

            val title: String = data?.getStringExtra(NotesActivity.EXTRA_TITLE).toString()
            val content:String= data?.getStringExtra(NotesActivity.EXTRA_CONTENT).toString()
            note=Note(title,content)
            viewModel.UpdateNoteOnEdit(note)
        } else {
            Toast.makeText(this,"Can't Save Note",Toast.LENGTH_SHORT).show()
        }

    }


}