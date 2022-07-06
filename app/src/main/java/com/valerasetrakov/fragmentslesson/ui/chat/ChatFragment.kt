package com.valerasetrakov.fragmentslesson.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.base.Repository
import com.valerasetrakov.fragmentslesson.databinding.FragmentChatBinding
import java.util.*

class ChatFragment(
    private val repository: Repository
): Fragment(R.layout.fragment_chat) {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var messages: List<MessagesView.Message> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatBinding.bind(requireView())
        loadMessages()
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun loadMessages() {
        val chatId = requireArguments().getString(CHAT_ID)
        requireNotNull(chatId)
        messages = repository.loadMessages(chatId)
        binding.messages.showMessages(messages)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CHAT_ID = "CHAT_ID"
    }
}