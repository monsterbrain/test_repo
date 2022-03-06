package com.faisal.employeedirectory.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class EmployeeEntity (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "username") var username: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "profileImage") var profileImage: String? = null,
    @ColumnInfo(name = "phone") var phone: String? = null,
    @ColumnInfo(name = "website") var website: String? = null
): Serializable
