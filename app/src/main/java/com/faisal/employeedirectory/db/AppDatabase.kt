package com.faisal.employeedirectory.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.faisal.employeedirectory.db.entity.AddressEntity
import com.faisal.employeedirectory.db.entity.CompanyEntity
import com.faisal.employeedirectory.db.entity.EmployeeEntity

@Database(entities = [EmployeeEntity::class, AddressEntity::class, CompanyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}