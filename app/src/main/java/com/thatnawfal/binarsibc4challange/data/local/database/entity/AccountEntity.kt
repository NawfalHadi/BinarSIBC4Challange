package com.thatnawfal.binarsibc4challange.data.local.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Dao
import kotlinx.parcelize.Parcelize
@Dao
@Parcelize
@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "username")
    var username: String?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "password")
    var password: String?
) : Parcelable