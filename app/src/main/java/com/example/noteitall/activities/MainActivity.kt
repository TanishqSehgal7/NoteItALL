package com.example.noteitall.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.core.view.marginBottom
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

class MainActivity : CoRoutineUtilityClass(), NotesRvAdapter.NoteItemClickListener,
    NotesRvAdapter.DeleteNoteOnLongClickListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var viewModel: NoteViewModelClass
    lateinit var notesRecyclerView: RecyclerView
    lateinit var deleteNoteBTN: ImageButton
    lateinit var ClosedeleteNoteSelectionBTN: ImageButton
    lateinit var searchView: androidx.appcompat.widget.SearchView
    lateinit var note: Note
    private val ADD_NOTE_REQ = 1
    private val EDIT_NOTE_REQ = 2
    lateinit var adapter: NotesRvAdapter

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
        adapter = NotesRvAdapter(this, this, this)
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

        notesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                Log.e("DY", "" + dy)
                if (dy < 0) {
                    addNote.visibility = ViewAnimator.VISIBLE
                    addNote.animate().translationY(0f).startDelay
                } else if (dy == 0) {
                    addNote.visibility = ViewAnimator.VISIBLE
                    addNote.animate().translationY(0f).startDelay
                } else {
                    addNote.animate().translationY(addNote.height.toFloat()+addNote.marginBottom).startDelay
                    addNote.visibility = ViewAnimator.GONE
                }
            }
        })

        searchView.isSubmitButtonEnabled=true
        searchView.setOnQueryTextListener(this)

    }

    override fun onBackPressed() {
        if (deleteNoteBTN.visibility == View.VISIBLE && ClosedeleteNoteSelectionBTN.visibility == View.VISIBLE && searchView.visibility == View.GONE) {
            deleteNoteBTN.visibility = View.GONE
            ClosedeleteNoteSelectionBTN.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }
    }

    override fun OnNoteClickListener(note: Note) {
        val intent = Intent(this, NotesActivity::class.java)

        deleteNoteBTN.visibility = View.GONE
        ClosedeleteNoteSelectionBTN.visibility=View.GONE
        searchView.visibility=View.VISIBLE

        intent.putExtra(NotesActivity.EXTRA_NOTE_ID, note.id)
        intent.putExtra(NotesActivity.EXTRA_TITLE, note.titleOFNote)
        intent.putExtra(NotesActivity.EXTRA_CONTENT, note.contentOFNote)
        startActivityForResult(intent, EDIT_NOTE_REQ)
        viewModel.DeleteNote(note)
        viewModel.UpdateNoteOnEdit(note)
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
            note=Note(title, content)
            viewModel.insertNewNote(note)
        } else if (requestCode==EDIT_NOTE_REQ && resultCode== RESULT_OK) {
            val id:Int = data!!.getIntExtra(NotesActivity.EXTRA_NOTE_ID, -1)
            if (id==-1){
                Toast.makeText(this, "Cannot Update Note", Toast.LENGTH_SHORT).show()
            }
            val title: String = data?.getStringExtra(NotesActivity.EXTRA_TITLE).toString()
            val content:String= data?.getStringExtra(NotesActivity.EXTRA_CONTENT).toString()
            note=Note(title, content)
            viewModel.UpdateNoteOnEdit(note)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query!=null){
            SearchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!=null){
            SearchNote(query)
        }
        return true
    }

    private fun SearchNote(query: String?){
        val searchNote="$query%"
        viewModel.SearchNoteDatabase(searchNote).observe(this,{list->
            list.let {
                adapter.UpdateListAfterAnyChanges(it)
            }
        })
    }

}