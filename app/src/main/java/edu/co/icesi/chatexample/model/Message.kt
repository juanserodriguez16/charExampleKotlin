package edu.co.icesi.chatexample.model

data class Message(
    var id:String = "",
    var message:String = "",
    var date:Long = 0,
    var authorId:String = "0"
)