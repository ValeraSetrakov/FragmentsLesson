package com.valerasetrakov.fragmentslesson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.valerasetrakov.fragmentslesson.base.Repository
import com.valerasetrakov.fragmentslesson.databinding.ActivityMainBinding
import com.valerasetrakov.fragmentslesson.ui.chat.ChatFragment
import com.valerasetrakov.fragmentslesson.ui.chats.ChatsFragment
import com.valerasetrakov.fragmentslesson.ui.filter.FilterFragment

/**
 * Хост Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setupFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showChats()
        }
        subscribeToChatSelection()
    }

    private fun subscribeToChatSelection() {
        supportFragmentManager.setFragmentResultListener(
            ChatsFragment.RESULT_KEY,
            this,
            ChatsResultListener()
        )
    }

    private fun setupFragmentFactory() {
        supportFragmentManager.fragmentFactory =
            CustomFragmentFactory(Repository())
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

    private class CustomFragmentFactory(
        private val repository: Repository
    ): FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when(className) {
                ChatsFragment::class.java.canonicalName -> ChatsFragment(repository)
                ChatFragment::class.java.canonicalName -> ChatFragment(repository)
                else -> super.instantiate(classLoader, className)
            }
        }
    }
}