package com.kacper.krakgo.screens.main.base_chat

import com.kacper.krakgo.model.ForumMessage
import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenter
import com.kacper.krakgo.mvp.MvpView

/**
 * Created by kacper on 04/02/2018.
 */
object BaseChatContract {
    interface View : MvpView {
        fun messageSendComplete()
        fun onMessagesDownload(messages: ArrayList<ForumMessage>)
    }
    interface Presenter : MvpPresenter<View> {
        fun sendMessage(message: String)
        fun getMessages()
        fun getConversationID(secondUserDetails: UserDetails)
        fun setID(conversationID:String)
    }
}