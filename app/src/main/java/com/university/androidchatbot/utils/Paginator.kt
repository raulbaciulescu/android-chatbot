package com.university.androidchatbot.utils

interface Paginator<Key, Item> {
    suspend fun loadNextItems(chatId: Int)
    fun reset()
}