package com.valerasetrakov.fragmentslesson.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.base.MessagesView
import com.valerasetrakov.fragmentslesson.base.Repository
import com.valerasetrakov.fragmentslesson.databinding.FragmentChatBinding

/**
 * Экран чата
 */
class ChatFragment(
    // TODO добавить передачу репозитория
): Fragment(R.layout.fragment_chat) {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var messages: List<MessagesView.Message> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreMessages(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatBinding.bind(requireView())
        loadMessages()
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(ChatFragment::class.java.simpleName, "Save state")
        TODO("Сохраняем список сообщений")
    }

    private fun loadMessages() {
        TODO("Проверяем не пустой ли уже список сообщений," +
                "если все норм, то отображаем его, если нет, то достаем из переданных параметров идентификатор," +
                "грузим сообщения, сохраняем их во внутреннем состоянии и отображаем")
    }

    private fun restoreMessages(savedInstanceState: Bundle?) {
        TODO("Восстанавливаем список сообщений")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CHAT_ID = "CHAT_ID"
        private const val MESSAGES_KEY = "MESSAGES_KEY"
    }
}