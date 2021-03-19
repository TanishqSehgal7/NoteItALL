package com.example.noteitall.adapters

import android.provider.ContactsContract
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteitall.R
import com.example.noteitall.entities.Note
import org.w3c.dom.Text

class NotesRvAdapter(private val notesList:List<Note>) :
    RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {
//
//    private lateinit var tvTitle:TextView
//    private lateinit var tvNoteContent:TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_card_layout,parent,false))
    }

    override fun onBindViewHolder(holder: NotesRvAdapter.NotesViewHolder, position: Int) {

//        tvTitle=holder.view.findViewById(R.id.RVNoteTitle)
//        tvNoteContent=holder.view.findViewById(R.id.RVNoteContent)

        holder.tvTitle.text=notesList[position].titleOfNote
        holder.tvNoteContent.text=notesList[position].contentOfNote

    }

    override fun getItemCount(): Int {
       return notesList.size
    }

    class NotesViewHolder(val view:View) : RecyclerView.ViewHolder(view){

        val tvTitle:TextView=view.findViewById(R.id.RVNoteTitle)
        val tvNoteContent:TextView=view.findViewById(R.id.RVNoteContent)
    }

}