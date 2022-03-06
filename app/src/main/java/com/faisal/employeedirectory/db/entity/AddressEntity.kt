package com.faisal.employeedirectory.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = EmployeeEntity::class,
        parentColumns = arrayOf("uid"),
        childColumns = ["employee_id"]
    )]
)
class AddressEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "street") var street: String? = null,
    @ColumnInfo(name = "suite") var suite: String? = null,
    @ColumnInfo(name = "city") var city: String? = null,
    @ColumnInfo(name = "zipcode") var zipcode: String? = null,
    // @ColumnInfo(name = "geo") var geo: GeoLocation? = null, todo
    @ColumnInfo(name = "employee_id") var employeeId: Int? = null
)