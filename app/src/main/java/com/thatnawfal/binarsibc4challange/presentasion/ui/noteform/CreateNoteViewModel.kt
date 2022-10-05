package com.thatnawfal.binarsibc4challange.presentasion.ui.noteform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateNoteViewModel(private val repository: LocalRepository): ViewModel() {
    val newNotes = MutableLiveData<Resource<Number>>()

    fun makesNewNotes(note: NotesEntity){
        viewModelScope.launch {
            newNotes.postValue(Resource.Loading())
            delay(500)
            newNotes.postValue(repository.insertNewNotes(note))
        }
    }

    fun getIdPreference(): Int? {
        return repository.getUserIdInPreference()
    }


}