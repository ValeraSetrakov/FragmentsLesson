package com.valerasetrakov.fragmentslesson.ui.chats.remove

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.databinding.DialogRemoveBinding

class RemoveDialog : DialogFragment(R.layout.dialog_remove) {

    private var _binding: DialogRemoveBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DialogRemoveBinding.bind(view)
        val dialogId = requireArguments().getString(DIALOG_ID_KEY)
        requireNotNull(dialogId)
        binding.confirmButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(
                RESULT_KEY,
                bundleOf(DIALOG_ID_KEY to dialogId)
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DIALOG_ID_KEY = "REMOVABLE_CHAT_ID"
        const val RESULT_KEY = "REMOVABLE_CHAT_RESULT"
    }
}