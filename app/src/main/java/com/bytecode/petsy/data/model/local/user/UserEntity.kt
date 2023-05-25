package com.bytecode.petsy.data.model.local.user

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = COLUMN_ID) val id: Long = 0,
    @ColumnInfo(name = COLUMN_EMAIL) val email: String,
    @ColumnInfo(name = COLUMN_PASSWORD) val password: String,
    @ColumnInfo(name = COLUMN_FIRST_NAME) val firstName: String,
    @ColumnInfo(name = COLUMN_LAST_NAME) val lastName: String,
    @ColumnInfo(name = COLUMN_COUNTRY) val country: String,
    @ColumnInfo(name = COLUMN_IS_LOGED_IN) val isLoggedIn: Boolean
) {
    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_IS_LOGED_IN = "is_logged_in"
    }
}