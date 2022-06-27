package com.valerasetrakov.fragmentslesson.ui.chats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.databinding.FragmentChatsBinding
import java.util.*

class ChatsFragment: Fragment(R.layout.fragment_chats) {

    private var _binding: FragmentChatsBinding? = null
    private val binding: FragmentChatsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatsBinding.bind(requireView())
        binding.chats.apply {
            showChats(chats)
            chatClickListener = ::showChat
        }
    }

    private fun showChat(chat: ChatsView.Chat) {
        parentFragmentManager.setFragmentResult(
            RESULT_KEY,
            bundleOf(SELECTED_CHAT_ID to chat.id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val RESULT_KEY = "SELECTED_CHAT"
        val SELECTED_CHAT_ID = "SELECTED_CHAT_ID"
        private val chats = listOf(
            ChatsView.Chat(UUID.randomUUID().toString(), "Валерий Сетраков", "Привет"),
            ChatsView.Chat(UUID.randomUUID().toString(), "Иван Перовин", "Привет"),
            ChatsView.Chat(UUID.randomUUID().toString(), "Дмитрий Кортунов", "Привет"),
        )
    }
}