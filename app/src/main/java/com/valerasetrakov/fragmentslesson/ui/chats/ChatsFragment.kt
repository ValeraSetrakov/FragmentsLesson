package com.valerasetrakov.fragmentslesson.ui.chats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.databinding.FragmentChatsBinding
import com.valerasetrakov.fragmentslesson.ui.chats.remove.RemoveDialog
import java.util.*

class ChatsFragment(
    private val filterSource: FilterConsumer
): Fragment(R.layout.fragment_chats) {

    private var _binding: FragmentChatsBinding? = null
    private val binding: FragmentChatsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatsBinding.bind(view)
        binding.chats.apply {
            showChats(chats)
            chatClickListener = ::showChat
            chatLongClickListener = ::suggestRemoveChat
        }
        childFragmentManager.setFragmentResultListener(
            RemoveDialog.RESULT_KEY,
            this,
            RemoveResultListener()
        )
        filterSource.subscribe(
            viewLifecycleOwner.lifecycleScope,
            ::filterChats
        )
        binding.toolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.filter -> {
                        parentFragmentManager.setFragmentResult(
                            OPEN_FILTER_KEY,
                            bundleOf(OPEN_FILTER_ID to true)
                        )
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showChat(chat: ChatsView.Chat) {
        parentFragmentManager.setFragmentResult(
            RESULT_KEY,
            bundleOf(SELECTED_CHAT_ID to chat.id)
        )
    }

    private fun suggestRemoveChat(chat: ChatsView.Chat): Boolean {
        RemoveDialog().apply {
            arguments = bundleOf(RemoveDialog.DIALOG_ID_KEY to chat.id)
        }.also { it.show(childFragmentManager, null) }
        return true
    }

    private fun filterChats(filter: FilterConsumer.FilterEvent) {
        when(filter) {
            FilterConsumer.FilterEvent.All -> {
                binding.chats.showChats(chats)
            }
            FilterConsumer.FilterEvent.OnlyUnread -> {
                binding.chats.showChats(chats.filter { chat -> !chat.isRead })
            }
            FilterConsumer.FilterEvent.OnlyRead -> {
                binding.chats.showChats(chats.filter { chat -> chat.isRead })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface FilterConsumer {
        fun subscribe(
            coroutineScope: LifecycleCoroutineScope,
            consumer: (FilterEvent) -> Unit
        )
        sealed class FilterEvent {
            object All: FilterEvent()
            object OnlyRead: FilterEvent()
            object OnlyUnread: FilterEvent()
        }
    }

    private inner class RemoveResultListener: FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            val removableDialogId = result.getString(RemoveDialog.DIALOG_ID_KEY)
            Toast.makeText(
                this@ChatsFragment.requireContext(),
                "Remove dialog id $removableDialogId",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        val RESULT_KEY = "SELECTED_CHAT"
        val SELECTED_CHAT_ID = "SELECTED_CHAT_ID"
        val OPEN_FILTER_KEY = "OPEN_FILTER_KEY"
        val OPEN_FILTER_ID = "OPEN_FILTER_ID"
        private val chats = listOf(
            ChatsView.Chat(UUID.randomUUID().toString(), "Валерий Сетраков", "Привет", false),
            ChatsView.Chat(UUID.randomUUID().toString(), "Иван Перовин", "Привет", true),
            ChatsView.Chat(UUID.randomUUID().toString(), "Дмитрий Кортунов", "Привет", true),
        )
    }
}