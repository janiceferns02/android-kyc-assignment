package com.example.kycapp.domain

enum class KycStatus {
    PENDING,
    VERIFIED
}

data class User (
    val id: Int,
    val fullName: String,
    val profileImage: String,
    val dob: String,
    val nationality: String,
    val accountNumber: String,
    val selfie: String?,
    val accountBalance: Int,
    val kycStatus: KycStatus
)