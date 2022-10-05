package com.thatnawfal.binarsibc4challange.data.local.database.dao

import androidx.room.*
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity

@Dao
interface NotesDao {
    @Query("SELECT * FROM NOTES WHERE account_id = :accountid")
    suspend fun getAllNotes(accountid: Int): List<NotesEntity>

    @Insert
    suspend fun insertNotes(notes: NotesEntity): Long

    @Update
    suspend fun updateNotes(notes: NotesEntity) : Int

    @Delete
    suspend fun deleteNotes(notes: NotesEntity) : Int

    @Query("SELECT * FROM NOTES WHERE id = :id")
    suspend fun getNotesWithId(id: Int): NotesEntity?

}