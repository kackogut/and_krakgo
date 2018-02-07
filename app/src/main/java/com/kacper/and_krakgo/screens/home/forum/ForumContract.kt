package com.kacper.and_krakgo.screens.home.forum

import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatContract

/**
 * Created by kacper on 25/01/2018.
 */
object ForumContract {
    interface Presenter : BaseChatContract.Presenter {
        override fun sendMessage(message: String)
        override fun getMessages()
    }
}