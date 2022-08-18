package com.ssafy.daero.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.daero.utils.constant.NOTIFICATION

@Entity(tableName = NOTIFICATION)
data class Notification(
    var title: String,
    var content: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
