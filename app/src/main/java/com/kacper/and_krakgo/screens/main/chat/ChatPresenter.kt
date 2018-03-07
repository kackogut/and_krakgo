package com.kacper.and_krakgo.screens.main.chat

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.model.ConversationDetails
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenterImpl
import com.kacper.and_krakgo.screens.home.forum.ForumPresenter
import com.kacper.and_krakgo.screens.main.base_chat.BaseChatContract
import java.util.*

/**
 * Created by kacper on 04/02/2018.
 */
class ChatPresenter : MvpPresenterImpl<BaseChatContract.View>(),
        ChatContract.Presenter {
    lateinit var mConversationID: String

    override fun sendMessage(message: String) {
        val forumMessage = ForumMessage(message, getCurrentUser())
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_CONVERSATIONS)
                .child(mConversationID)
                .child( forumMessage.time.toString() + getCurrentUser().uid.substring(0,6))
                .setValue(forumMessage)
                .addOnCompleteListener({
                    mView?.messageSendComplete()
                })
                .addOnFailureListener({
                    mView?.showError(it)
                })

    }

    override fun getMessages() {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.USER_CONVERSATIONS)
                .child(mConversationID)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {
                        Log.w(ChatPresenter::class.java.simpleName,
                                "getMessages:onCancelled", p0?.toException());
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        val values: ArrayList<ForumMessage> = ArrayList()
                        if (p0!!.exists()) {
                            p0.children.mapTo(values) {
                                it.getValue(ForumMessage::class.java)!!
                            }

                        }
                        mView?.onMessagesDownload(values)
                    }

                })
    }

    override fun getConversationID(secondUserDetails:UserDetails) {
        getDatabaseReference()
                .child(FirebaseDatabaseHelper.ALL_CONVERSATIONS)
                .child(getCurrentUser().uid)
                .child(secondUserDetails.userID)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                        Log.w(ChatPresenter::class.java.simpleName,
                                "getConversation:onCancelled", p0?.toException());
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        if (p0!!.exists()) {
                            mConversationID = p0.getValue(ConversationDetails::class.java)!!.conversationID
                        } else {
                            mConversationID = getDatabaseReference()
                                    .child(FirebaseDatabaseHelper.USER_CONVERSATIONS)
                                    .push().key
                            
                            getDatabaseReference()
                                    .child(FirebaseDatabaseHelper.ALL_CONVERSATIONS)
                                    .child(getCurrentUser().uid)
                                    .child(secondUserDetails.userID)
                                    .setValue(ConversationDetails(
                                            mConversationID,
                                            secondUserDetails.photo_url,
                                            secondUserDetails.display_name,
                                            Date().time
                                    ))

                            getDatabaseReference()
                                    .child(FirebaseDatabaseHelper.ALL_CONVERSATIONS)
                                    .child(secondUserDetails.userID)
                                    .child(getCurrentUser().uid)
                                    .setValue(ConversationDetails(
                                            mConversationID,
                                            getCurrentUser().photoUrl.toString(),
                                            getCurrentUser().displayName,
                                            Date().time
                                    ))
                        }
                        getMessages()
                    }
                })
    }
}