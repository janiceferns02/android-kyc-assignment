package com.example.kycapp.data

import com.example.kycapp.data.local.AppPreferences
import com.example.kycapp.data.local.UserDao
import com.example.kycapp.data.mapper.toDomain
import com.example.kycapp.data.mapper.toEntity
import com.example.kycapp.data.remote.UsersApi
import com.example.kycapp.domain.User
import com.example.kycapp.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi,
    private val userDao: UserDao,
    private val appPreferences: AppPreferences
): UserRepository {
    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { userEntities ->
            userEntities.map { entity -> entity.toDomain() }
        }
    }

    override suspend fun refreshUsers() {
        val lastSync = appPreferences.getLastSyncTimestamp()
        val currentTime = System.currentTimeMillis()
        val twentyFourHours = TimeUnit.HOURS.toMillis(24)

        if(currentTime - lastSync > twentyFourHours) {
            try {
                val userResponse = usersApi.getUsers()

                val userEntities = userResponse.users.map { dto -> dto.toEntity() }

                userDao.clearAllUsers()
                userDao.insertUsers(userEntities)

                appPreferences.saveLastSyncTimestamp(currentTime)

            } catch (e: Exception) {

            }
        }
    }
}