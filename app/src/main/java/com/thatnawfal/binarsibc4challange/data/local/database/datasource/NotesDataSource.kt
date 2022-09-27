package com.thatnawfal.binarsibc4challange.data.local.database.datasource

import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.dao.NotesDao
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity

interface NotesDataSource {
    suspend fun insertNewNotes(notes: NotesEntity): Long
    suspend fun getAllNotesById(accountId: Int): List<NotesEntity>
}

class NotesDataSourceImpl(private var notesDao: NotesDao): NotesDataSource {
    override suspend fun insertNewNotes(notes: NotesEntity): Long {
        return notesDao.insertNotes(notes)
    }

    override suspend fun getAllNotesById(accountId: Int): List<NotesEntity> {
        return notesDao.getAllNotes(accountId)
    }

}