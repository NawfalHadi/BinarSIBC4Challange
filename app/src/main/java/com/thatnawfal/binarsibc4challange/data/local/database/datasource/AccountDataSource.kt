package com.thatnawfal.binarsibc4challange.data.local.database.datasource

import com.thatnawfal.binarsibc4challange.data.local.database.dao.AccountDao
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity

interface AccountDataSource {
    suspend fun getIdFromEmail(email: String): Int

    suspend fun checkEmailExcist(email: String): Boolean
    suspend fun registerAccount(account: AccountEntity): Long
    suspend fun checkPassword(email: String, password: String): Boolean

}

class AccountDataSourceImpl(private var accountDao: AccountDao): AccountDataSource {
    override suspend fun getIdFromEmail(email: String): Int {
        return accountDao.getIdFromEmail(email)
    }

    override suspend fun checkEmailExcist(email: String): Boolean {
        return accountDao.emailExcistCheck(email) > 0
    }

    override suspend fun registerAccount(account: AccountEntity): Long {
        return accountDao.registerAccount(account)
    }

    override suspend fun checkPassword(email: String, password: String): Boolean {
        return accountDao.passwordIsCorrect(email, password) > 0
    }

}