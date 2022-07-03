package com.valerasetrakov.fragmentslesson

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.valerasetrakov.fragmentslesson.databinding.ActivityMainBinding
import com.valerasetrakov.fragmentslesson.ui.chat.ChatFragment
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsFragment
import com.valerasetrakov.fragmentslesson.ui.filter.FilterFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory =
            CustomFragmentFactory(FilterCommunicator())
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
            supportFragmentManager.setFragmentResultListener(
                ChatsFragment.OPEN_FILTER_KEY,
                this,
                OpenFilterListener()
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

    private inner class OpenFilterListener: FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<FilterFragment>(binding.root.id)
                addToBackStack(null)
            }
        }
    }

    private class CustomFragmentFactory(
        private val communicator: FilterCommunicator
    ): FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when(className) {
                ChatsFragment::class.java.canonicalName -> ChatsFragment(communicator)
                FilterFragment::class.java.canonicalName -> FilterFragment(communicator)
                else -> super.instantiate(classLoader, className)
            }
        }
    }
}