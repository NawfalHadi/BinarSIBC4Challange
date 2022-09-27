package com.thatnawfal.binarsibc4challange.presentasion.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.databinding.ItemNotesBinding

class NotesAdapter(): RecyclerView.Adapter<NotesAdapter.NotesListViewHolder>() {
    private lateinit var dataSet: List<NotesEntity>

    fun submitData(value: List<NotesEntity>) {
        dataSet = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        val binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        val note = dataSet[position]
        holder.bindingView(note)
    }

    override fun getItemCount(): Int = dataSet.size

    class NotesListViewHolder(
        private val binding: ItemNotesBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bindingView(note: NotesEntity) {
            with(itemView) {
                with(binding) {
                    tvItemNotesTitle.text = note.judul
                    tvItemNotesNote.text = note.catatan
                }
            }
        }

    }
}
