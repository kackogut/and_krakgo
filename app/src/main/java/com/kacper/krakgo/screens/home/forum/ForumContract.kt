package com.kacper.krakgo.screens.home.forum

import com.kacper.krakgo.screens.main.base_chat.BaseChatContract

/**
 * Created by kacper on 25/01/2018.
 */
object ForumContract {
    interface Presenter : BaseChatContract.Presenter {
        override fun sendMessage(message: String)
        override fun getMessages()
    }
}