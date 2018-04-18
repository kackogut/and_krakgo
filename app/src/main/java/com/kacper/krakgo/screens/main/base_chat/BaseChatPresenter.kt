package com.kacper.krakgo.screens.main.base_chat

import com.kacper.krakgo.model.UserDetails
import com.kacper.krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 04/02/2018.
 */
open class BaseChatPresenter : MvpPresenterImpl<BaseChatContract.View>(),
        BaseChatContract.Presenter {
    override fun setID(conversationID: String) {

    }

    override fun getConversationID(secondUserDetails: UserDetails) {

    }

    override fun sendMessage(message: String) {

    }

    override fun getMessages() {

    }
}