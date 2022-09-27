package com.thatnawfal.binarsibc4challange.presentasion.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: LocalRepository): ViewModel() {
    val notesListResult = MutableLiveData<Resource<List<NotesEntity>>>()

    fun getNotesList(accountId: Int){
        viewModelScope.launch {
            notesListResult.postValue(Resource.Loading())
            delay(1000)
            notesListResult.postValue(repository.getAllNotesById(accountId))
        }
    }

    fun getIdPreference(): Int? {
        return repository.getUserIdInPreference()
    }


}