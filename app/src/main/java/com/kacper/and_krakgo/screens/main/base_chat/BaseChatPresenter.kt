package com.kacper.and_krakgo.screens.main.base_chat

import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenterImpl

/**
 * Created by kacper on 04/02/2018.
 */
open class BaseChatPresenter : MvpPresenterImpl<BaseChatContract.View>(),
        BaseChatContract.Presenter {
    override fun setID(conversationID: String) {

    }

    override fun getConversationID(userDetails: UserDetails) {

    }

    override fun sendMessage(message: String) {

    }

    override fun getMessages() {

    }
}