package com.example.owlingo.database.community

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Comment::class], version = 1, exportSchema = false)
abstract class SubCommentDatabase : RoomDatabase() {

    abstract val subCommentDatabaseDao: SubCommentDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: SubCommentDatabase? = null

        fun getInstance(context: Context): SubCommentDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubCommentDatabase::class.java,
                        "owlingo_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}