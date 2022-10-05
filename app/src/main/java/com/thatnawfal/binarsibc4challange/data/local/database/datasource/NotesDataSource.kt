package com.thatnawfal.binarsibc4challange.data.local.database.datasource

import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.data.local.database.dao.NotesDao
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity

interface NotesDataSource {
    suspend fun getAllNotesById(accountId: Int): List<NotesEntity>
    suspend fun insertNewNotes(notes: NotesEntity): Long
    suspend fun updateNotes(notes: NotesEntity): Int
    suspend fun deleteNotes(notes: NotesEntity): Int
    suspend fun getNotesById(id: Int): NotesEntity?
}

class NotesDataSourceImpl(private var notesDao: NotesDao): NotesDataSource {
    override suspend fun getAllNotesById(accountId: Int): List<NotesEntity> {
        return notesDao.getAllNotes(accountId)
    }

    override suspend fun insertNewNotes(notes: NotesEntity): Long {
        return notesDao.insertNotes(notes)
    }

    override suspend fun updateNotes(notes: NotesEntity): Int {
        return notesDao.updateNotes(notes)
    }

    override suspend fun deleteNotes(notes: NotesEntity): Int {
        return notesDao.deleteNotes(notes)
    }

    override suspend fun getNotesById(id: Int): NotesEntity? {
        return notesDao.getNotesWithId(id)
    }


}