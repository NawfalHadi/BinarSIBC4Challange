package com.thatnawfal.binarsibc4challange.data.local.database.dao

import androidx.room.*
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT COUNT() FROM ACCOUNT WHERE email = :email")
    suspend fun emailExcistCheck(email: String): Int

    @Query("SELECT COUNT() FROM ACCOUNT WHERE email = :email AND password = :password")
    suspend fun passwordIsCorrect(email: String, password: String): Int

    @Query("SELECT id FROM ACCOUNT WHERE email = :email")
    suspend fun getIdFromEmail(email: String): Int

    @Query("SELECT * FROM ACCOUNT WHERE id = :id")
    suspend fun getDataUser(id: Int): AccountEntity

    @Insert
    suspend fun registerAccount(account: AccountEntity) : Long
}