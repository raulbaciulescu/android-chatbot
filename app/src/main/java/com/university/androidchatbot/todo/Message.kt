package com.university.androidchatbot.todo

enum class MessageType {
    USER, AI
}

data class Message(
    val text: String,
    var type: MessageType = MessageType.USER,
    var chatId: Int = 4
)