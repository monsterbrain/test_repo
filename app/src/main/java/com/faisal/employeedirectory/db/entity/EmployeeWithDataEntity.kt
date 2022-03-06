package com.faisal.employeedirectory.db.entity

import androidx.room.Embedded
import androidx.room.Relation

class EmployeeWithDataEntity (
    @Embedded val employee: EmployeeEntity,

    @Relation(parentColumn = "uid", entityColumn = "employee_id")
    val address: AddressEntity
)