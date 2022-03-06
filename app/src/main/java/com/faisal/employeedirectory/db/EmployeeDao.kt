package com.faisal.employeedirectory.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.faisal.employeedirectory.db.entity.AddressEntity
import com.faisal.employeedirectory.db.entity.EmployeeEntity
import com.faisal.employeedirectory.db.entity.EmployeeWithDataEntity

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM EmployeeEntity")
    fun getAll(): List<EmployeeEntity>

    @Query("SELECT * FROM EmployeeEntity")
    fun getAllDataWithDetails(): List<EmployeeWithDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employeeEntity: EmployeeEntity): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(addressEntity: AddressEntity): Long?
}