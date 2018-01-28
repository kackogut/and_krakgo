package com.kacper.and_krakgo.screens.home.forum

import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.mvp.MvpPresenter
import com.kacper.and_krakgo.mvp.MvpView

/**
 * Created by kacper on 25/01/2018.
 */
object ForumContract {
    interface View : MvpView{
        fun messageSendComplete()
        fun onMessagesDownload(messages: ArrayList<ForumMessage>)
    }
    interface Presenter : MvpPresenter<View>{
        fun sendMessage(message: String)
        fun getMessages()
    }
}