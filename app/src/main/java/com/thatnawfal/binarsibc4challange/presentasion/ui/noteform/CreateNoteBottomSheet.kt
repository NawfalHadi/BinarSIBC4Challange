package com.thatnawfal.binarsibc4challange.presentasion.ui.noteform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.databinding.FragmentCreateNoteBottomSheetBinding
import com.thatnawfal.binarsibc4challange.di.ServiceLocator
import com.thatnawfal.binarsibc4challange.utills.viewModelFactory

class CreateNoteBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateNoteBottomSheetBinding
    private var listener: OnIdUserChangedListener? = null

    private val viewModel: CreateNoteViewModel by viewModelFactory {
        CreateNoteViewModel(ServiceLocator.provideLocalRepository(requireContext()))
    }

    fun setListener(listener: OnIdUserChangedListener){
        this.listener = listener
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
        binding.tvBottomSheetCreateNoteId.text = context?.let {
            viewModel.getIdPreference().toString()
        }

        binding.btnBottomSheetCreateNoteId.setOnClickListener {
            val newIdUser = 1
            context?.let { viewModel.setIdPreference(newIdUser) }
            listener?.onIdUserChanged()
            dismiss()
        }
    }
}

interface OnIdUserChangedListener{
    fun onIdUserChanged()
}