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
    private val repository: Repository
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
        outState.putSerializable(MESSAGES_KEY, ArrayList(messages))
    }

    private fun loadMessages() {
        val chatId = requireArguments().getString(CHAT_ID)
        requireNotNull(chatId)
        if (messages.isEmpty())
            messages = repository.loadMessages(chatId)
        binding.messages.showMessages(messages)
    }

    private fun restoreMessages(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            Log.d(ChatFragment::class.java.simpleName, "Restore state")
            messages = it.getSerializable(MESSAGES_KEY) as? List<MessagesView.Message>
                ?: emptyList()
        }
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