package edu.co.icesi.chatexample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import edu.co.icesi.chatexample.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainViewModel:ViewModel() {
    private var userId = "IKO5NcJxqf1qu58N6wDZ"
    private var otherUserID = "WZV8LQC99T5cbev1KoFq"
    private val arrayMessages = arrayListOf<Message>()
    private val _messages: MutableLiveData<ArrayList<Message> > = MutableLiveData(arrayListOf())
    val messages: LiveData<ArrayList<Message>> get() = _messages


    fun subscribeToMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = Firebase.firestore.collection("chat")
                .document(userId).collection("rooms")
                .whereEqualTo("friendId", otherUserID).get().await()
            for (doc in result.documents) {
                val chat = doc.toObject(Chat::class.java)
                withContext(Dispatchers.Main){subscribeRealTimeMessages(chat!!)}
            }
        }
    }

    fun subscribeRealTimeMessages(chat:Chat){
        Firebase.firestore
            .collection("messages")
            .document(chat.id)
            .collection("message").addSnapshotListener{ data, e ->
                for(doc in data!!.documentChanges){
                    if(doc.type.name == "ADDED"){
                        val msg = doc.document.toObject(Message::class.java)
                        Log.e(">>>", msg.message)
                        arrayMessages.add(msg)
                        _messages.value = arrayMessages
                    }
                }
            }
    }

}



data class Chat(
    val id:String = "",
    val members:ArrayList<String> = arrayListOf()
)

data class User(
    val id: String = "",
    val name: String = ""
)