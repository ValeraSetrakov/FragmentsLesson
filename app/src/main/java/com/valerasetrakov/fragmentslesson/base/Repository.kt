package com.valerasetrakov.fragmentslesson.base

import com.valerasetrakov.fragmentslesson.ui.chat.MessagesView
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsView
import java.util.*

class Repository {

    fun loadChats(): List<ChatsView.Chat> {
        return chats
    }

    fun loadMessages(chatId: String): List<MessagesView.Message> {
        return messages
    }

    private companion object {
        val chats = listOf(
            ChatsView.Chat(UUID.randomUUID().toString(), "Валерий Сетраков", "Привет", false),
            ChatsView.Chat(UUID.randomUUID().toString(), "Иван Перовин", "Привет", true),
            ChatsView.Chat(UUID.randomUUID().toString(), "Дмитрий Кортунов", "Привет", true),
        )
        val messages = listOf(
            MessagesView.Message(id = UUID.randomUUID().toString(), message = "Привет"),
            MessagesView.Message(id = UUID.randomUUID().toString(), message = "Как дела?"),
            MessagesView.Message(id = UUID.randomUUID().toString(), message = "Что делаешь?"),
            MessagesView.Message(id = UUID.randomUUID().toString(), message = "Не хочешь увидеться?"),
        )
    }
}