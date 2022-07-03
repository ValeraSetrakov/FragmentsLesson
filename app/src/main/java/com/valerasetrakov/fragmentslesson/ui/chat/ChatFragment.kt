package com.valerasetrakov.fragmentslesson.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.databinding.FragmentChatBinding
import java.util.*

class ChatFragment: Fragment(R.layout.fragment_chat) {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatBinding.bind(requireView())
        val chatId = requireArguments().getString(CHAT_ID)
        requireNotNull(chatId)
        loadMessages(chatId)
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun loadMessages(chatId: String) {
        binding.messages.apply {
            showMessages(messages)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CHAT_ID = "CHAT_ID"
        private val messages = listOf(
            MessagesView.Message(
                id = UUID.randomUUID().toString(),
                message = "Привет"
            ),
            MessagesView.Message(
                id = UUID.randomUUID().toString(),
                message = "Как дела?"
            ),
            MessagesView.Message(
                id = UUID.randomUUID().toString(),
                message = "Что делаешь?"
            ),
            MessagesView.Message(
                id = UUID.randomUUID().toString(),
                message = "Не хочешь увидеться?"
            ),
        )
    }
}