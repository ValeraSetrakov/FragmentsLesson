package com.valerasetrakov.fragmentslesson.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.valerasetrakov.fragmentslesson.databinding.ItemChatBinding
import java.io.Serializable

/**
 * [View] для отображения списка чатов
 */
class ChatsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    var chatClickListener: (Chat) -> Unit = {}
    var chatLongClickListener: (Chat) -> Boolean = { false }

    private val adapter = Adapter()

    init {
        layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        setAdapter(adapter)
    }

    /**
     * Отображает список [chats]
     */
    fun showChats(chats: Collection<Chat>) {
        adapter.setChats(chats)
    }

    /**
     * Информация о чате
     * @param id идентификатор чата
     * @param title название чата
     * @param message видимое сообщение
     * @param isRead если новых сообщений нет, то false, иначе true
     */
    data class Chat(
        val id: String,
        val title: CharSequence,
        val message: CharSequence,
        val isRead: Boolean
    ): Serializable

    private inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private val chats: MutableCollection<Chat> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolder(ItemChatBinding.inflate(layoutInflater, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(chats.elementAt(position))
        }

        override fun getItemCount(): Int {
            return chats.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setChats(chats: Collection<Chat>) {
            this.chats.clear()
            this.chats.addAll(chats)
            notifyDataSetChanged()
        }

        private inner class ViewHolder(
            private val binding: ItemChatBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(chat: Chat) {
                binding.root.setOnClickListener {
                    chatClickListener(chat)
                }
                binding.root.setOnLongClickListener {
                    chatLongClickListener(chat)
                }
                binding.title.text = chat.title
                binding.message.text = chat.message
            }
        }
    }
}