package com.valerasetrakov.fragmentslesson.ui.chats.remove

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Диалог для удаления чата
 */
class RemoveChatDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        TODO("Возвращаем AlertDialog")
        return super.onCreateDialog(savedInstanceState)
    }

    private fun removeDialog() {
        TODO("Отправляем событие подтверждения удаления чата")
    }

    companion object {
        const val CHAT_ID_KEY = "REMOVABLE_CHAT_ID"
        const val RESULT_KEY = "REMOVABLE_CHAT_RESULT"
    }
}