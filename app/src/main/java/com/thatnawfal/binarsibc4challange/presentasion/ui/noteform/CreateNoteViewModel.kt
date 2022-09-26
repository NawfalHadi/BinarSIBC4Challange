package com.thatnawfal.binarsibc4challange.presentasion.ui.noteform

import androidx.lifecycle.ViewModel
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository

class CreateNoteViewModel(private val repository: LocalRepository): ViewModel() {
    fun setIdPreference(newId: Int){
        repository.setUserIdInPreference(newId)
    }

    fun getIdPreference(): Int? {
        return repository.getUserIdInPreference()
    }
}