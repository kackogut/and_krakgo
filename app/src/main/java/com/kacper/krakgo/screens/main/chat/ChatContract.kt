package com.kacper.krakgo.screens.main.chat

import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.screens.main.base_chat.BaseChatContract

/**
 * Created by kacper on 04/02/2018.
 */
object ChatContract {
    interface Presenter : BaseChatContract.Presenter {
        override fun sendMessage(message: String)
        override fun getMessages()
        override fun getConversationID(secondUserDetails: UserDetails)
        override fun setID(conversationID:String)
    }
}