package com.thatnawfal.binarsibc4challange.data.repository

import com.thatnawfal.binarsibc4challange.data.local.preference.AuthPreference
import com.thatnawfal.binarsibc4challange.data.local.preference.AuthPreferenceDataSource

interface LocalRepository {

    // 1. CheckKalauIdAdalah != 0
    fun checkIfUserLogin(): Boolean
    fun checkIfEmailAndPasswordCorrect(email: String, pass: String): Boolean
    fun setUserIdInPreference(newId: Int)
    fun getUserIdInPreference(): Int?
}

class LocalRepositoryImpl(

    private val dataSource : AuthPreferenceDataSource
) : LocalRepository {

    override fun checkIfUserLogin(): Boolean {
        return dataSource.getUserId() != 0
    }

    override fun checkIfEmailAndPasswordCorrect(email: String, pass: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun setUserIdInPreference(newId: Int) {
        dataSource.setUserId(newId)
    }

    override fun getUserIdInPreference(): Int? {
        return dataSource.getUserId()
    }

}