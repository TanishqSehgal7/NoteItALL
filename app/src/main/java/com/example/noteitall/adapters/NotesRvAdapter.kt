package com.example.noteitall.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteitall.DataBase.NotesDataBase
import com.example.noteitall.R
import com.example.noteitall.activities.NotesActivity
import com.example.noteitall.entities.Note
import kotlinx.coroutines.channels.consumesAll
import org.w3c.dom.Text
import java.util.ArrayList


class NotesRvAdapter(private val context: Context,private val noteItemClickListener: NoteItemClickListener) : RecyclerView.Adapter<NotesRvAdapter.NotesViewHolder>() {

    private var notesList= ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val viewHolder=NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_card_layout,parent,false))

        viewHolder.deleteNoteCheckBox.visibility=View.INVISIBLE

        viewHolder.itemView.setOnLongClickListener(View.OnLongClickListener {
            viewHolder.itemView.requestFocus()
            viewHolder.deleteNoteCheckBox.visibility=View.VISIBLE
            if (viewHolder.deleteNoteCheckBox.isChecked) {
                noteItemClickListener.OnNoteClickListener(notesList[viewHolder.adapterPosition])
            }
            true
        })

        return viewHolder
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote=notesList[position]
        holder.tvTitle.text=currentNote.titleOFNote
        holder.tvNoteContent.text=currentNote.contentOFNote

        holder.itemView.setOnClickListener {
            Toast.makeText(context,"Long Click the note to Delete",Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener {
            holder.deleteNoteCheckBox.visibility=View.VISIBLE
                noteItemClickListener.OnNoteClickListener(currentNote)
            true
        }
    }

    fun UpdateListAfterAnyChanges(updatedList : List<Note>){
        notesList = (updatedList as ArrayList<Note>).clone() as ArrayList<Note>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return notesList.size
    }

    class NotesViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val tvTitle:TextView=view.findViewById(R.id.RVNoteTitle)
        val tvNoteContent:TextView=view.findViewById(R.id.RVNoteContent)
        val deleteNoteCheckBox: CheckBox =view.findViewById(R.id.SelectdeleteNote)
    }

    interface NoteItemClickListener{
        fun OnNoteClickListener(note:Note)
    }

}