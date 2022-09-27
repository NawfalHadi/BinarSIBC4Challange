package com.thatnawfal.binarsibc4challange.presentasion.ui.editnoteform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EditNoteViewModel(private val repository: LocalRepository): ViewModel() {
    val updateResult = MutableLiveData<Resource<Number>>()

    fun updateNotes(notes: NotesEntity) {
        viewModelScope.launch {
            updateResult.postValue(Resource.Loading())
            delay(1000)
            updateResult.postValue(repository.updateNotes(notes))
        }
    }
}