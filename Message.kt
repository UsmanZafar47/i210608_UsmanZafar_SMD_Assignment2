package com.example.myapplication

data class Message(
    var id: String = "",
    var senderId: String = "",
    var receiverId: String = "",
    var message: String? = null,
    var imageUrl: String? = null,
    var voiceUrl: String? = null,
    var videoUrl: String? = null,
    var fileUrl: String? = null,
    var timestamp: Long = System.currentTimeMillis()
)
