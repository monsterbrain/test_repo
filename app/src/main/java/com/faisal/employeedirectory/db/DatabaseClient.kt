package com.faisal.employeedirectory.db

import android.content.Context
import androidx.room.Room

class DatabaseClient(context: Context) {

    private var database: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "EmployeeDB")
            .fallbackToDestructiveMigration() // todo migration queries to be added
            .allowMainThreadQueries()
            .build()

    fun getEmployeeDao(): EmployeeDao {
        return database.employeeDao()
    }

    companion object {
        private var singleInstance: DatabaseClient? = null

        fun init(context: Context) {
            if (singleInstance == null) {
                singleInstance = DatabaseClient(context)
            }
        }

        fun instance(): DatabaseClient {
            if (singleInstance == null) {
                throw Exception("Database is not initialized")
            }

            return singleInstance!!
        }
    }
}