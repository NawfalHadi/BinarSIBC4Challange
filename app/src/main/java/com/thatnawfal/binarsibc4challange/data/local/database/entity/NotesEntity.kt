package com.thatnawfal.binarsibc4challange.data.local.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "account_id")
    var account_id: Int,

    @ColumnInfo(name = "judul")
    var judul: String?,

    @ColumnInfo(name = "catatan")
    var catatan: String?
) : Parcelable