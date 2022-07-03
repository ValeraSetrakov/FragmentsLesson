package com.valerasetrakov.fragmentslesson.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.databinding.FragmentFilterBinding

class FilterFragment(
    private val filterConsumer: FilterProducer
): Fragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterBinding.bind(view)
        binding.typeOfChatsRg.setOnCheckedChangeListener { _, filterId ->
            chooseFilter(filterId.getFilterById())
        }
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun chooseFilter(filter: FilterProducer.FilterEvent) {
        filterConsumer.sendFilterEvent(filter)
    }

    private fun Int.getFilterById(): FilterProducer.FilterEvent {
        return when(this) {
            binding.allRb.id -> FilterProducer.FilterEvent.All
            binding.readRb.id -> FilterProducer.FilterEvent.OnlyRead
            binding.unreadRb.id -> FilterProducer.FilterEvent.OnlyUnread
            else -> error("Unsupported filter type")
        }
    }

    interface FilterProducer {
        fun sendFilterEvent(filter: FilterEvent)

        sealed class FilterEvent {
            object All: FilterEvent()
            object OnlyRead: FilterEvent()
            object OnlyUnread: FilterEvent()
        }
    }
}