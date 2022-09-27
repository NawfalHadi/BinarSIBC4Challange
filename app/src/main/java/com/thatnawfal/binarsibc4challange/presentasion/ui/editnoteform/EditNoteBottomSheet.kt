package com.thatnawfal.binarsibc4challange.presentasion.ui.editnoteform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.databinding.FragmentEditNoteBottomSheetBinding

class EditNoteBottomSheet(private val notes: NotesEntity) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditNoteBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialData()
    }

    private fun initialData() {
        binding.etBotSheetTitleEdit.setText(notes.judul)
        binding.etBotSheetNotesEdit.setText(notes.catatan)
    }


}