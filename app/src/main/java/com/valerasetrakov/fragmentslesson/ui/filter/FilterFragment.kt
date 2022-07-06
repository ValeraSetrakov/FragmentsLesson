package com.valerasetrakov.fragmentslesson.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.base.FilterEvent
import com.valerasetrakov.fragmentslesson.databinding.FragmentFilterBinding

/**
 * Экран фильтров
 */
class FilterFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterBinding.bind(view)
        binding.typeOfChatsRg.setOnCheckedChangeListener { _, filterId ->
            chooseFilter(filterId.getFilterById())
        }
    }

    private fun chooseFilter(filter: FilterEvent) {
        TODO("Отсылаем выбранный фильтр")
    }

    private fun Int.getFilterById(): FilterEvent {
        return when(this) {
            binding.allRb.id -> FilterEvent.ALL
            binding.readRb.id -> FilterEvent.ONLY_READ
            binding.unreadRb.id -> FilterEvent.ONLY_UNREAD
            else -> error("Unsupported filter type")
        }
    }

    companion object {
        const val CHOOSED_FILTER_KEY = "CHOOSED_FILTER_KEY"
    }
}