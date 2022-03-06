package com.faisal.employeedirectory.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.faisal.employeedirectory.models.GeoLocation
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = EmployeeEntity::class,
        parentColumns = arrayOf("uid"),
        childColumns = ["employee_id"]
    )]
)
class CompanyEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "catchPhrase") var catchPhrase: String? = null,
    @ColumnInfo(name = "bs") var bs: String? = null,
    @ColumnInfo(name = "employee_id") var employeeId: Int? = null
): Serializable