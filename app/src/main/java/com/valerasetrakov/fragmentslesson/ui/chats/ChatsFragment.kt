package com.valerasetrakov.fragmentslesson.ui.chats

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.base.ChatsView
import com.valerasetrakov.fragmentslesson.base.FilterEvent
import com.valerasetrakov.fragmentslesson.base.Repository
import com.valerasetrakov.fragmentslesson.databinding.FragmentChatsBinding
import com.valerasetrakov.fragmentslesson.ui.chats.remove.RemoveChatDialog
import com.valerasetrakov.fragmentslesson.ui.filter.FilterFragment
import kotlin.collections.ArrayList

/**
 * Экран списка чатов
 */
class ChatsFragment(
    private val repository: Repository
): Fragment(R.layout.fragment_chats) {

    private var _binding: FragmentChatsBinding? = null
    private val binding: FragmentChatsBinding get() = _binding!!

    private var chats: List<ChatsView.Chat> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreChats(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentChatsBinding.bind(view)
        loadChats()
        initViews()
        subscribeToRemoveChatDialog()
        subscribeToFilter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(ChatsFragment::class.java.simpleName, "Save state")
        outState.putSerializable(CHATS_KEY, ArrayList(chats))
    }

    private fun loadChats() {
        if (chats.isEmpty())
            chats = repository.loadChats()
    }

    private fun restoreChats(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            Log.d(ChatsFragment::class.java.simpleName, "Restore state")
            chats = it.getSerializable(CHATS_KEY) as? List<ChatsView.Chat>
                ?: emptyList()
        }
    }

    private fun initViews() {
        initList()

        binding.toolbar.apply {
            setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.filter -> {
                        showFilters()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initList() {
        binding.chats.apply {
            showChats(chats)
            chatClickListener = ::showChat
            chatLongClickListener = ::suggestRemoveChat
        }
    }

    private fun subscribeToRemoveChatDialog() {
        childFragmentManager.setFragmentResultListener(
            RemoveChatDialog.RESULT_KEY,
            this,
            RemoveResultListener()
        )
    }

    private fun subscribeToFilter() {
        childFragmentManager.setFragmentResultListener(
            FilterFragment.CHOOSED_FILTER_KEY,
            this,
            FilterResultListener()
        )
    }

    private fun showFilters() {
        FilterFragment()
            .show(childFragmentManager, null)
    }

    private fun showChat(chat: ChatsView.Chat) {
        parentFragmentManager.setFragmentResult(
            RESULT_KEY,
            bundleOf(SELECTED_CHAT_ID to chat.id)
        )
    }

    private fun suggestRemoveChat(chat: ChatsView.Chat): Boolean {
        RemoveChatDialog().apply {
            arguments = bundleOf(RemoveChatDialog.CHAT_ID_KEY to chat.id)
        }.also { it.show(childFragmentManager, null) }
        return true
    }

    private fun filterChats(filter: FilterEvent) {
        when(filter) {
            FilterEvent.ALL -> {
                binding.chats.showChats(chats)
            }
            FilterEvent.ONLY_UNREAD -> {
                binding.chats.showChats(chats.filter { chat -> !chat.isRead })
            }
            FilterEvent.ONLY_READ -> {
                binding.chats.showChats(chats.filter { chat -> chat.isRead })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class RemoveResultListener: FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            val removableDialogId = result.getString(RemoveChatDialog.CHAT_ID_KEY)
            chats.find { it.id == removableDialogId }
                ?.let { chats - it }
                ?.also { binding.chats.showChats(it) }
        }
    }

    private inner class FilterResultListener: FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            val filter = result.getSerializable(FilterFragment.CHOOSED_FILTER_KEY) as FilterEvent
            filterChats(filter)
        }
    }

    companion object {
        const val RESULT_KEY = "SELECTED_CHAT"
        const val SELECTED_CHAT_ID = "SELECTED_CHAT_ID"
        private const val CHATS_KEY = "CHATS_KEY"
    }
}