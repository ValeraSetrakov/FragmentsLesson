package com.valerasetrakov.fragmentslesson

import androidx.lifecycle.LifecycleCoroutineScope
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsFragment.FilterConsumer
import com.valerasetrakov.fragmentslesson.ui.filter.FilterFragment.FilterProducer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsFragment.FilterConsumer.FilterEvent as ConsumerFilter
import com.valerasetrakov.fragmentslesson.ui.filter.FilterFragment.FilterProducer.FilterEvent as ProducerFilter

class FilterCommunicator: FilterConsumer, FilterProducer {

    private val stateFlow = MutableStateFlow<ConsumerFilter>(ConsumerFilter.All)

    override fun subscribe(
        coroutineScope: LifecycleCoroutineScope,
        consumer: (ConsumerFilter) -> Unit
    ) {
        coroutineScope.launchWhenResumed {
            stateFlow.collect {
                consumer(it)
            }
        }
    }

    override fun sendFilterEvent(filter: ProducerFilter) {
        stateFlow.value = filter.mapToConsumerFilter()
    }

    private fun ProducerFilter.mapToConsumerFilter(): ConsumerFilter {
        return when(this) {
            ProducerFilter.All -> ConsumerFilter.All
            ProducerFilter.OnlyRead -> ConsumerFilter.OnlyRead
            ProducerFilter.OnlyUnread -> ConsumerFilter.OnlyUnread
        }
    }
}