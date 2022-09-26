package com.thatnawfal.binarsibc4challange.presentasion.ui.registerform

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: LocalRepository): ViewModel() {
    val isEmailExcist = MutableLiveData<Boolean>()

    val newAccount = MutableLiveData<Resource<Number>>()

    fun checkEmailExcist(email: String){
        viewModelScope.launch {
            isEmailExcist.postValue(repository.isEmailExcist(email))
        }
    }

    fun registerNewAccount(account: AccountEntity) {
        viewModelScope.launch {
            newAccount.postValue(Resource.Loading())
            delay(1000)
            newAccount.postValue(repository.registerAccount(account))
        }
    }



}