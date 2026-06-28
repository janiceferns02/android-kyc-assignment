package com.example.kycapp.data.local

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPrefs = context.getSharedPreferences("kyc_app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_USER_SYNC = "last_user_sync_timestamp"
    }

    fun saveLastSyncTimestamp(timestamp: Long) {
        sharedPrefs.edit { putLong(KEY_LAST_USER_SYNC, timestamp) }
    }

    fun getLastSyncTimestamp(): Long {
        return sharedPrefs.getLong(KEY_LAST_USER_SYNC, 0L)
    }
}