package edu.co.icesi.chatexample.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import edu.co.icesi.chatexample.databinding.ActivityMainBinding
import edu.co.icesi.chatexample.view.ChatAdapter
import edu.co.icesi.chatexample.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel:MainViewModel by viewModels()
    private val adapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.chatRV.adapter = ChatAdapter()
        binding.chatRV.layoutManager = LinearLayoutManager(this)
        binding.chatRV.setHasFixedSize(true)
        viewModel.subscribeToMessages()
        viewModel.messages.observe(this){
           if (it.size >= 1){
               val m = it.last()
               adapter.addMessage(m)
           }
        }

    }
}