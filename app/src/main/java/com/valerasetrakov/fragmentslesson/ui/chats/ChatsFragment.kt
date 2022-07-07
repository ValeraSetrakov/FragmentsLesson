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
    // TODO добавить передачу репозитория
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
        // todo добавить сохранение списка чатов
    }

    private fun loadChats() {
        TODO("Вначале нужно проверить загружали ли мы уже список чатов," +
                "если все норм, то отображаем его, если нет, то грузим," +
                "сохраняем в переменную и отображаем")
    }

    private fun restoreChats(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            Log.d(ChatsFragment::class.java.simpleName, "Restore state")
            TODO("Восстанавливаем список чатов")
        }
    }

    private fun saveChats(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            TODO("Сохраняем список чатов")
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
        TODO("Подписаться на подтверждение удаления чата")
    }

    private fun subscribeToFilter() {
        TODO("Подписываемся на выбор фильтра")
    }

    private fun showFilters() {
        TODO("Отображаем диалог с выбором фильтра")
    }

    private fun showChat(chat: ChatsView.Chat) {
        TODO("Добавить отправку события отображения чата")
    }

    private fun suggestRemoveChat(chat: ChatsView.Chat): Boolean {
        TODO("Отображаем диалог подтверждения удаления чата")
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

    companion object {
        const val RESULT_KEY = "SELECTED_CHAT"
        const val SELECTED_CHAT_ID = "SELECTED_CHAT_ID"
        private const val CHATS_KEY = "CHATS_KEY"
    }
}