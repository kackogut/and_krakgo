package com.kacper.krakgo.model

import com.google.firebase.auth.FirebaseUser
import com.kacper.krakgo.KrakGoApp

import java.util.Date

/**
 * Created by kacper on 25/01/2018.
 */

class Message {

    lateinit var message_text: String
    lateinit var userID: String
    var time: Long = -1
    lateinit var userName: String
    lateinit var avatarUrl: String

    constructor() {
        //Constructor needed for firebase data parse
    }

    constructor(message_text: String, currentUser: FirebaseUser) {
        this.message_text = message_text
        this.userID = currentUser.uid
        this.userName = currentUser.displayName!!
        this.avatarUrl = currentUser.photoUrl!!.toString()
        this.time = Date().time
    }
}
