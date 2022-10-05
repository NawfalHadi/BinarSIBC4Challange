package com.thatnawfal.binarsibc4challange.presentasion.ui.editnoteform

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.catnip.mypassword.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.databinding.FragmentEditNoteBottomSheetBinding
import com.thatnawfal.binarsibc4challange.di.ServiceLocator
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.CreateNoteViewModel
import com.thatnawfal.binarsibc4challange.utills.viewModelFactory

class EditNoteBottomSheet(private val notes: NotesEntity, private var listener: OnChangeListnerEdited) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditNoteBottomSheetBinding

    private val viewModel: EditNoteViewModel by viewModelFactory {
        EditNoteViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

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

        binding.btnBotSheetSubmit.setOnClickListener {
            submitNoteForm()
        }

        observeAction()
    }

    private fun observeAction() {
        viewModel.updateResult.observe(requireActivity()) {
            when (it) {
                is Resource.Error -> Toast.makeText(requireActivity(), "error", Toast.LENGTH_LONG).show()
                is Resource.Loading -> Toast.makeText(requireActivity(), "", Toast.LENGTH_SHORT).show()
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Success update note",
                        Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    private fun initialData() {
        binding.etBotSheetTitleEdit.setText(notes.judul)
        binding.etBotSheetNotesEdit.setText(notes.catatan)
    }

    private fun formValidation() : Boolean{
        val judul = binding.etBotSheetTitleEdit.text.toString()
        val catatan = binding.etBotSheetNotesEdit.text.toString()
        var validateForm = true

        if (judul.isEmpty()) {
            validateForm = false
            binding.tilBotSheetTitleEdit.isErrorEnabled = true
            binding.tilBotSheetTitleEdit.error = "Title Can't Be Empty"
        } else {binding.tilBotSheetTitleEdit.isErrorEnabled = false}

        if (catatan.isEmpty()) {
            validateForm = false
            binding.tilBotSheetNotesEdit.isErrorEnabled = true
            binding.tilBotSheetNotesEdit.error = "Notes Can't Be Empty"
        } else {binding.tilBotSheetNotesEdit.isErrorEnabled = false}

        return validateForm
    }

    private fun getNotesFromForm() : NotesEntity{
        return NotesEntity(
            id = notes.id,
            account_id = notes.account_id,
            judul = binding.etBotSheetTitleEdit.text.toString(),
            catatan =  binding.etBotSheetNotesEdit.text.toString()
        )
    }

    private fun submitNoteForm(){
        if (formValidation()) {
            viewModel.updateNotes(getNotesFromForm())
            listener.onNoteEdited()
        }
    }
}

interface OnChangeListnerEdited {
    fun onNoteEdited()
}