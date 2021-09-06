package com.samarth.ktornoteapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samarth.ktornoteapp.R
import com.samarth.ktornoteapp.data.local.models.LocalNote
import com.samarth.ktornoteapp.databinding.ItemNoteBinding

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding:ItemNoteBinding):RecyclerView.ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<LocalNote>(){
        override fun areItemsTheSame(oldItem: LocalNote, newItem: LocalNote): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: LocalNote, newItem: LocalNote): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)
    var notes:List<LocalNote>
        get() = differ.currentList
        set(value) = differ.submitList(value)






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = notes[position]
        holder.binding.apply {
            noteText.isVisible = note.noteTitle != null
            noteDescription.isVisible = note.desription != null

            note.noteTitle?.let {
                noteText.text = it
            }
            note.desription?.let {
                noteDescription.text = it
            }

            noteSync.setBackgroundResource(
                if (note.connected) R.drawable.synced
                else R.drawable.not_sync
            )

            root.setOnClickListener {
                onItemClickListener?.invoke(note)
            }

        }


    }


    private var onItemClickListener: ((LocalNote)-> Unit)? = null
    fun setOnItemClickListener(listener: (LocalNote)->Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return notes.size



    }
}