package com.thatnawfal.binarsibc4challange.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity

@Dao
interface NotesDao {
    @Query("SELECT * FROM NOTES WHERE account_id = :accountid")
    suspend fun getAllNotes(accountid: Int): List<NotesEntity>

    @Insert
    suspend fun insertNotes(notes: NotesEntity): Long

}