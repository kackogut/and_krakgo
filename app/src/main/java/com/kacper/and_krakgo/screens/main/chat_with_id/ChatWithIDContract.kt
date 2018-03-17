package com.kacper.and_krakgo.screens.main.chat_with_id

import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatContract

/**
 * Created by kacper on 04/02/2018.
 */
object ChatWithIDContract {
    interface Presenter : BaseChatContract.Presenter {
        override fun sendMessage(message: String)
        override fun getMessages()
        override fun getConversationID(secondUserDetails: UserDetails)
        override fun setID(conversationID:String)
    }
}