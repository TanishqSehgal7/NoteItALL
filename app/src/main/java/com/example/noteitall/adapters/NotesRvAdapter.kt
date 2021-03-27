package com.example.noteitall.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteitall.R
import com.example.noteitall.activities.NotesActivity
import com.example.noteitall.entities.Note
import org.w3c.dom.Text
import java.util.ArrayList

class NotesRvAdapter(private val context: Context) : RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {
//
//    private lateinit var tvTitle:TextView
//    private lateinit var tvNoteContent:TextView

    private var notesList= ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val viewHolder=NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_card_layout,parent,false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesRvAdapter.NotesViewHolder, position: Int) {

//        tvTitle=holder.view.findViewById(R.id.RVNoteTitle)
//        tvNoteContent=holder.view.findViewById(R.id.RVNoteContent)
        val currentNote=notesList[position]
        holder.tvTitle.text=currentNote.titleOFNote
        holder.tvNoteContent.text=currentNote.contentOFNote

        holder.view.setOnClickListener {
            // open the saved note
        }

    }

    fun UpdateListAfterAnyChanges(updatedList : List<Note>){
        notesList = (updatedList as ArrayList<Note>).clone() as ArrayList<Note>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return notesList.size
    }

    class NotesViewHolder(val view:View) : RecyclerView.ViewHolder(view){

        val tvTitle:TextView=view.findViewById(R.id.RVNoteTitle)
        val tvNoteContent:TextView=view.findViewById(R.id.RVNoteContent)
    }

//    interface NotesItemClick {
//
//        fun NoteClicked(note: Note)
//
//    }


}