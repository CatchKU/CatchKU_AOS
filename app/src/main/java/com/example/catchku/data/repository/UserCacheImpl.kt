package com.example.catchku.data.repository

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserCache @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveUserId(id: Int) {
        with(sharedPreferences.edit()) {
            putInt(KEY_USER_ID, id)
            apply()
        }
    }

    fun getSaveUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -12314)
    }

    companion object {
        private const val KEY_USER_ID = "USER_ID"
    }
}