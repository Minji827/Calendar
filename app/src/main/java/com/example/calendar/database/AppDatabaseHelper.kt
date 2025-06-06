package com.example.calendar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.calendar.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabaseHelper : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabaseHelper? = null

        fun getDatabase(context: Context): AppDatabaseHelper {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabaseHelper::class.java,
                    "calendar_app_db"
                ).allowMainThreadQueries() // 졸작용 간단 버전 (비추천: 실서비스에서는 Coroutine 사용)
                    .build()
            }
            return INSTANCE!!
        }
    }
}
