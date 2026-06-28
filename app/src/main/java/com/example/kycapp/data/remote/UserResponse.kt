package com.example.kycapp.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @param: Json(name = "users") val users: List<UserDto>
)

@JsonClass(generateAdapter = true)
data class UserDto(
    @param: Json(name = "id") val id: Int,
    @param: Json(name = "firstName") val firstName: String,
    @param: Json(name = "lastName") val lastName: String,
    @param: Json(name = "image") val image: String,
    @param: Json(name = "address") val address: AddressDto,
    @param: Json(name = "bank") val bank: BankDto,
    @param: Json(name = "birthDate") val birthDate: String
)

@JsonClass(generateAdapter = true)
data class AddressDto(
    @param: Json(name = "country") val country: String
)

@JsonClass(generateAdapter = true)
data class BankDto(
    @param: Json(name = "iban") val iban: String
)