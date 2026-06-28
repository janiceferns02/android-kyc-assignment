package com.example.kycapp.data.mapper

import com.example.kycapp.data.local.UserEntity
import com.example.kycapp.data.remote.UserDto
import com.example.kycapp.domain.KycStatus
import com.example.kycapp.domain.User
import kotlin.random.Random

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        fullName = fullName,
        profileImage = profileImage,
        dob = dob,
        nationality = nationality,
        accountNumber = accountNumber,
        selfie = selfie,
        accountBalance = accountBalance,
        kycStatus = kycStatus
    )
}

fun UserDto.toEntity(): UserEntity {
    val randomBalance = Random.nextInt(1000, 150000)

    return UserEntity(
        id = id,
        fullName = "$firstName $lastName",
        profileImage = image,
        dob = birthDate,
        nationality = address.country,
        accountNumber = bank.iban,
        selfie = null,
        accountBalance = randomBalance,
        kycStatus = KycStatus.PENDING
    )
}