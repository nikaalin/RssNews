package com.example.rssnews.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        var INSTANCE: ArticleDatabase? = null

        fun getAppDataBase(context: Context): ArticleDatabase? {
            if (INSTANCE == null) {
                synchronized(ArticleDatabase::class) {
                    INSTANCE =
                        Room.databaseBuilder(context, ArticleDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}