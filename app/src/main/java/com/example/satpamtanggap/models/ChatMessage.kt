package com.example.satpamtanggap.models

import java.util.Date


class ChatMessage {
    var message: String? = null
    var senderId: String? = null
    var timestamp: String? = null
    var image: String? = null

    var convId: String? = null
    var convName: String? = null
    var convImage: String? = null

    constructor(){}

    constructor(message: String?, senderId: String?, timestamp: String?){
        this.message = message
        this.senderId = senderId
        this.timestamp = timestamp
    }

//    constructor(convId: String?, convName: String?, convImage: String?){
//        this.convId = convId
//        this.convName = convName
//        this.convImage = convImage
//    }
}