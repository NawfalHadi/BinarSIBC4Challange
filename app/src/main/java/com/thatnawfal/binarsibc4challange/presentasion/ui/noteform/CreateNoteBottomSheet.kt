package com.thatnawfal.binarsibc4challange.presentasion.ui.noteform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thatnawfal.binarsibc4challange.wrapper.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.databinding.FragmentCreateNoteBottomSheetBinding
import com.thatnawfal.binarsibc4challange.di.ServiceLocator
import com.thatnawfal.binarsibc4challange.utills.viewModelFactory

class CreateNoteBottomSheet(private var listener: OnChangeListenerCreate) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateNoteBottomSheetBinding

    private val viewModel: CreateNoteViewModel by viewModelFactory {
        CreateNoteViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNoteBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBotSheetSubmit.setOnClickListener {
            submitNoteForm()
        }

        observeAction()

    }

    private fun observeAction() {
        viewModel.newNotes.observe(requireActivity()) {
            when (it) {
                is Resource.Error -> {
                    setFormEnabled(true)
                    Toast.makeText(requireContext(), "There's Error When Inputting Data",
                        Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    setFormEnabled(false)
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Success submit note",
                        Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    private fun setFormEnabled(b: Boolean) {
        with(binding) {
            etBotSheetNotes.isEnabled = false
            etBotSheetTitle.isEnabled = false
        }
    }

    private fun formValidation() : Boolean{
        val judul = binding.etBotSheetTitle.text.toString()
        val catatan = binding.etBotSheetNotes.text.toString()
        var validateForm = true

        if (judul.isEmpty()) {
            validateForm = false
            binding.tilBotSheetTitle.isErrorEnabled = true
            binding.tilBotSheetTitle.error = "Title Can't Be Empty"
        } else {binding.tilBotSheetTitle.isErrorEnabled = false}

        if (catatan.isEmpty()) {
            validateForm = false
            binding.tilBotSheetNotes.isErrorEnabled = true
            binding.tilBotSheetNotes.error = "Notes Can't Be Empty"
        } else {binding.tilBotSheetNotes.isErrorEnabled = false}

        return validateForm
    }

    private fun getNotesFromForm() : NotesEntity{
        return NotesEntity(
            account_id = viewModel.getIdPreference().toString().toInt(),
            judul = binding.etBotSheetTitle.text.toString(),
            catatan =  binding.etBotSheetNotes.text.toString()
        )
    }

    private fun submitNoteForm(){
        if (formValidation()) {
            viewModel.makesNewNotes(getNotesFromForm())
            listener.onNoteCreated()
        }
    }
}

interface OnChangeListenerCreate{
    fun onNoteCreated()
}