package com.valerasetrakov.fragmentslesson.ui.chats.remove

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class RemoveChatDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Предупреждение")
            .setMessage("Вы точно хотите удалить этот диалог?")
            .setPositiveButton("Да") { _, _ -> removeDialog() }
            .setNegativeButton("Нет") { _, _ -> dismiss() }
            .create()
    }

    private fun removeDialog() {
        parentFragmentManager.setFragmentResult(
            RESULT_KEY, requireArguments()
        )
    }

    companion object {
        const val CHAT_ID_KEY = "REMOVABLE_CHAT_ID"
        const val RESULT_KEY = "REMOVABLE_CHAT_RESULT"
    }
}