package com.kacper.and_krakgo.screens.main.base_chat

import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView

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
    }
}