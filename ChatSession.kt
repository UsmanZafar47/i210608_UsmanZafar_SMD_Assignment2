package com.example.myapplication

data class ChatSession(
    var id: String = "",
    var mentorId: String = "",
    var menteeId: String = "",
    var lastMessage: String = "",
    var lastMessageTimestamp: Long = System.currentTimeMillis()
)
