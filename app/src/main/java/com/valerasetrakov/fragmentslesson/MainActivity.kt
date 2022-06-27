package com.valerasetrakov.fragmentslesson

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.valerasetrakov.fragmentslesson.databinding.ActivityMainBinding
import com.valerasetrakov.fragmentslesson.ui.chat.ChatFragment
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showChats()
            supportFragmentManager.setFragmentResultListener(
                ChatsFragment.RESULT_KEY,
                this,
                ChatsResultListener()
            )
        }
    }

    private fun showChats() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ChatsFragment>(binding.root.id)
        }
    }

    private fun showChat(chatId: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                binding.root.id, ChatFragment::class.java,
                bundleOf(ChatFragment.CHAT_ID to chatId)
            )
            addToBackStack(null)
        }
    }

    private inner class ChatsResultListener: FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            val chatId = result.getString(ChatsFragment.SELECTED_CHAT_ID)
            requireNotNull(chatId)
            showChat(chatId)
        }
    }
}