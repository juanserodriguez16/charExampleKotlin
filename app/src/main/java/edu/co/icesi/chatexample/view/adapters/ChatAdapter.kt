package edu.co.icesi.chatexample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.co.icesi.chatexample.model.Message
import edu.co.icesi.chatexample.R
import edu.co.icesi.chatexample.databinding.OwnMessageBinding


class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = ArrayList<Message>()
    private var userId = "IKO5NcJxqf1qu58N6wDZ"
    init {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        lateinit var skeleton: RecyclerView.ViewHolder
        when (viewType) {
            0 -> {
                skeleton = OwnChatMessageHolder(inflater.inflate(R.layout.own_message, parent, false))

            }
            1 -> {
                skeleton = OtherChatMessageHolder(inflater.inflate(R.layout.other_message, parent, false))
            }
        }
        return skeleton
    }

    override fun getItemViewType(position: Int): Int {
        if (messages[position].authorId == "Alfa") {
            return 0;
        }else{
            return 1;
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when(holder.itemViewType){
            0->{
                val skeleton = holder as OwnChatMessageHolder
                skeleton.messageTV.text = message.message
            }
            1->{
                val skeleton = holder as OtherChatMessageHolder
                skeleton.messageTV.text = message.message
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
    fun addMessage(m:Message){
        messages.add(m)
        notifyItemInserted(messages.lastIndex)

    }
}

class OwnChatMessageHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val binding:OwnMessageBinding = OwnMessageBinding.bind(itemView)
    val messageTV = binding.messageTV
}

class OtherChatMessageHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val binding:OwnMessageBinding = OwnMessageBinding.bind(itemView)
    val messageTV = binding.messageTV
}