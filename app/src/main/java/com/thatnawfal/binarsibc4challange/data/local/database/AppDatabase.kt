package com.thatnawfal.binarsibc4challange.data.local.database

import android.content.Context
import net.sqlcipher.database.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thatnawfal.binarsibc4challange.data.local.database.dao.AccountDao
import com.thatnawfal.binarsibc4challange.data.local.database.dao.NotesDao
import com.thatnawfal.binarsibc4challange.data.local.database.entity.AccountEntity
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import net.sqlcipher.database.SQLiteDatabase.getBytes
import net.sqlcipher.database.SupportFactory

@Database(entities = [AccountEntity::class, NotesEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun notesDao(): NotesDao

    companion object {
        private const val DB_NAME = "db_notes.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val passphrase: ByteArray = SQLiteDatabase.getBytes("db_notes-hashed".toCharArray())
                val factory = SupportFactory(passphrase)

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .openHelperFactory(factory)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
//        private var INSTANCE: AppDatabase = null
//
//        fun getInstance(context: Context): AppDatabase {
//            if (INSTANCE == null) {
//                synchronized(AppDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                    AppDatabase::class.java, DB_NAME).build()
//                }
//            }
//
//            return INSTANCE
//        }
//
//        fun destroyInstance(){
//            INSTANCE = null
//        }

    }
}