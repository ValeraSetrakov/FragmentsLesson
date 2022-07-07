package com.valerasetrakov.fragmentslesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.valerasetrakov.fragmentslesson.base.Repository
import com.valerasetrakov.fragmentslesson.databinding.ActivityMainBinding
import com.valerasetrakov.fragmentslesson.ui.chat.ChatFragment
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsFragment
import com.valerasetrakov.fragmentslesson.ui.filter.FilterFragment

/**
 * Хост Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // todo добавить вызов настройки фрабрики фрагментов
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // todo установить стартовый фрагмент
        // todo добавить подписку на выбор чата
    }

    private fun subscribeToChatSelection() {
        TODO("Подписываемся на результат выбора чата")
    }

    private fun setupFragmentFactory() {
        TODO("Создаем и настраиваем фабрику фрагментов")
    }

    private fun showChats() {
        TODO("Отображаем список чатов")
    }

    private fun showChat(chatId: String) {
        TODO("Отображаем чат")
    }
}