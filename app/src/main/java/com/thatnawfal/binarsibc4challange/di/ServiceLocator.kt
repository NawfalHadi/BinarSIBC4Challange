package com.thatnawfal.binarsibc4challange.di

import android.content.Context
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

    fun provideLocalRepository(context: Context): LocalRepository {
        return LocalRepositoryImpl(provideAuthPreferenceDataSource(context))
    }
}