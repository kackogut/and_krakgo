package com.kacper.krakgo.screens.home.forum

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.krakgo.model.Message
import com.kacper.krakgo.screens.main.base_chat.BaseChatPresenter
import kotlin.collections.ArrayList

/**
 * Created by kacper on 25/01/2018.
 */
class ForumPresenter : BaseChatPresenter() {
    override fun getMessages() {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.FORUM_MESSAGES)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                        Log.w(ForumPresenter::class.java.simpleName,
                                "loadMessages:onCancelled", p0?.toException());
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        val values: ArrayList<Message> = ArrayList()
                        if (p0!!.exists()) {
                            p0.children.mapTo(values) {
                                it.getValue(Message::class.java)!!
                            }

                        }
                        mView?.onMessagesDownload(values)
                    }

                })
    }

    override fun sendMessage(message: String) {
        val forumMessage = Message(message, getCurrentUser()!!)
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.FORUM_MESSAGES)
                .child( forumMessage.time.toString() + getCurrentUser()!!.uid.substring(0,6))
                .setValue(forumMessage)
                .addOnCompleteListener({
                    mView?.messageSendComplete()
                })
                .addOnFailureListener({
                    mView?.showError(it)
                })

    }


}