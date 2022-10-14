package com.thatnawfal.binarsibc4challange.presentasion.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity
import com.thatnawfal.binarsibc4challange.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: LocalRepository): ViewModel() {
    val notesListResult = MutableLiveData<Resource<List<NotesEntity>>>()
    val notesDetailResult = MutableLiveData<Resource<NotesEntity?>>()
    val deleteResult = MutableLiveData<Resource<Number>>()
    val userDetailResult = MutableLiveData<Resource<AccountEntity>>()

    fun getDataUser(id: Int){
        viewModelScope.launch {
            userDetailResult.postValue(Resource.Loading())
            val account = repository.getDataUser(id)
            userDetailResult.postValue(account)
        }
    }

    fun getNotesList(accountId: Int){
        viewModelScope.launch {
            notesListResult.postValue(Resource.Loading())
            delay(1000)
            notesListResult.postValue(repository.getAllNotesById(accountId))
        }
    }

    fun getNotesById(id: Int){
        viewModelScope.launch {
            notesDetailResult.postValue(Resource.Loading())
            delay(1000)
            notesDetailResult.postValue(repository.getNotesById(id))
        }
    }

    fun deleteNotes(notes: NotesEntity) {
        viewModelScope.launch {
            deleteResult.postValue(Resource.Loading())
            delay(500)
            deleteResult.postValue(repository.deleteNotes(notes))
        }
    }

    fun getIdPreference(): Int? {
        return repository.getUserIdInPreference()
    }

    fun setIdPreference(newId: Int){
        repository.setUserIdInPreference(newId)
    }
}