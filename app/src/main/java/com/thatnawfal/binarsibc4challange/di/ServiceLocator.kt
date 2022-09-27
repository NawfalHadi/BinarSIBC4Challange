package com.thatnawfal.binarsibc4challange.di

import android.content.Context
import com.thatnawfal.binarsibc4challange.data.local.database.AppDatabase
import com.thatnawfal.binarsibc4challange.data.local.database.dao.AccountDao
import com.thatnawfal.binarsibc4challange.data.local.database.dao.NotesDao
import com.thatnawfal.binarsibc4challange.data.local.database.datasource.AccountDataSource
import com.thatnawfal.binarsibc4challange.data.local.database.datasource.AccountDataSourceImpl
import com.thatnawfal.binarsibc4challange.data.local.database.datasource.NotesDataSource
import com.thatnawfal.binarsibc4challange.data.local.database.datasource.NotesDataSourceImpl
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity
import com.thatnawfal.binarsibc4challange.data.local.preference.AuthPreference
import com.thatnawfal.binarsibc4challange.data.local.preference.AuthPreferenceDataSource
import com.thatnawfal.binarsibc4challange.data.local.preference.AuthPreferenceDataSourceImpl
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepository
import com.thatnawfal.binarsibc4challange.data.repository.LocalRepositoryImpl

object ServiceLocator {

    fun provideAuthPreference(context: Context) : AuthPreference {
        return AuthPreference(context)
    }

    fun provideAuthPreferenceDataSource(context: Context) : AuthPreferenceDataSource {
        return AuthPreferenceDataSourceImpl(provideAuthPreference(context))
    }

    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    /*** table account ***/

    fun provideAccountDao(context: Context): AccountDao {
        return provideAppDatabase(context).accountDao()
    }

    fun provideAccountDataSource(context: Context): AccountDataSource {
        return AccountDataSourceImpl(provideAccountDao(context))
    }

    /*** table notes ***/

    fun provideNotesDao(context: Context): NotesDao {
        return provideAppDatabase(context).notesDao()
    }

    fun provideNotesDataSource(context: Context): NotesDataSource {
        return NotesDataSourceImpl(provideNotesDao(context))
    }


    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            provideAuthPreferenceDataSource(context),
            provideAccountDataSource(context),
            provideNotesDataSource(context)
        )
    }
}