package com.thatnawfal.binarsibc4challange.data.local.preference

interface AuthPreferenceDataSource {
    fun getUserId(): Int?
    fun setUserId(newUserId: Int)
}

class AuthPreferenceDataSourceImpl(
    private val authPreference: AuthPreference
) : AuthPreferenceDataSource {

    override fun getUserId(): Int? {
        return authPreference.userId
    }

    override fun setUserId(newUserId: Int) {
        authPreference.userId = newUserId
    }


}