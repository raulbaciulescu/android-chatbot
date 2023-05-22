package com.university.androidchatbot.todo.v1

interface Paginator<Key, Item> {
    suspend fun loadNextItems(chatId: Int)
    fun reset()
}