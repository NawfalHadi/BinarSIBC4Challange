package com.thatnawfal.binarsibc4challange.data.repository

import com.thatnawfal.binarsibc4challange.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.datasource.AccountDataSource
import com.thatnawfal.binarsibc4challange.data.local.database.datasource.NotesDataSource
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.data.local.preference.AuthPreferenceDataSource

interface LocalRepository {

    // 1. CheckKalauIdAdalah != 0
    fun checkIfUserLogin(): Boolean
    fun setUserIdInPreference(newId: Int)
    fun getUserIdInPreference(): Int?

    suspend fun getIdFromEmail(email: String): Int
    suspend fun getDataUser(id: Int): Resource<AccountEntity>
    suspend fun registerAccount(account: AccountEntity): Resource<Number>
    suspend fun isEmailExcist(email: String): Boolean
    suspend fun isPassCorrect(email: String, password: String): Boolean

    suspend fun insertNewNotes(notes: NotesEntity): Resource<Number>
    suspend fun getAllNotesById(accountId: Int): Resource<List<NotesEntity>>
    suspend fun updateNotes(notes: NotesEntity): Resource<Number>
    suspend fun deleteNotes(notes: NotesEntity): Resource<Number>
    suspend fun getNotesById(id: Int): Resource<NotesEntity?>
}

class LocalRepositoryImpl(
    private val dataSource : AuthPreferenceDataSource,
    private val accountDataSource: AccountDataSource,
    private val notesDataSource: NotesDataSource
) : LocalRepository {

    /*** Shared Preferences ***/

    override fun checkIfUserLogin(): Boolean {
        return dataSource.getUserId() != 0
    }


    override fun setUserIdInPreference(newId: Int) {
        dataSource.setUserId(newId)
    }

    override fun getUserIdInPreference(): Int? {
        return dataSource.getUserId()
    }

    /*** Account ***/

    override suspend fun getIdFromEmail(email: String): Int {
        return accountDataSource.getIdFromEmail(email)
    }

    override suspend fun getDataUser(id: Int): Resource<AccountEntity> {
        return proceed {
            accountDataSource.getDataUser(id)
        }
    }

    override suspend fun registerAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.registerAccount(account)
        }
    }

    override suspend fun isEmailExcist(email: String): Boolean {
        return accountDataSource.checkEmailExcist(email)
    }

    override suspend fun isPassCorrect(email: String, password: String): Boolean {
        return accountDataSource.checkPassword(email, password)
    }

    /*** Notes ***/


    override suspend fun insertNewNotes(notes: NotesEntity): Resource<Number> {
        return proceed {
            notesDataSource.insertNewNotes(notes)
        }
    }

    override suspend fun getAllNotesById(accountId: Int): Resource<List<NotesEntity>> {
        return proceed {
            notesDataSource.getAllNotesById(accountId)
        }
    }

    override suspend fun updateNotes(notes: NotesEntity): Resource<Number> {
        return proceed {
            notesDataSource.updateNotes(notes)
        }
    }

    override suspend fun deleteNotes(notes: NotesEntity): Resource<Number> {
        return proceed {
            notesDataSource.deleteNotes(notes)
        }
    }

    override suspend fun getNotesById(id: Int): Resource<NotesEntity?> {
        return proceed {
            notesDataSource.getNotesById(id)
        }
    }


    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}