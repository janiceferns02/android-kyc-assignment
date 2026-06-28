package com.example.kycapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kycapp.domain.KycStatus

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val fullName: String,
    val profileImage: String,
    val dob: String,
    val nationality: String,
    val accountNumber: String,
    val selfie: String?,
    val accountBalance: Int,
    val kycStatus: KycStatus
)