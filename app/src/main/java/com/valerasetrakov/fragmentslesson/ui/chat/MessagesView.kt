package com.valerasetrakov.fragmentslesson.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valerasetrakov.fragmentslesson.R
import com.valerasetrakov.fragmentslesson.databinding.ItemChatBinding
import com.valerasetrakov.fragmentslesson.databinding.ItemMessageBinding

class MessagesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private val adapter = Adapter()

    init {
        layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        setAdapter(adapter)
    }

    fun showMessages(chats: Collection<Message>) {
        adapter.setMessages(chats)
    }

    data class Message(
        val id: String,
        val message: CharSequence
    )

    private class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private val messages: MutableCollection<Message> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolder(ItemMessageBinding.inflate(layoutInflater, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(messages.elementAt(position))
        }

        override fun getItemCount(): Int {
            return messages.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setMessages(chats: Collection<Message>) {
            this.messages.clear()
            this.messages.addAll(chats)
            notifyDataSetChanged()
        }

        private class ViewHolder(
            private val binding: ItemMessageBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(message: Message) {
                binding.message.text = message.message
            }
        }
    }
}