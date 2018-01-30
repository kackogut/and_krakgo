package com.kacper.and_krakgo.screens.home.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.kacper.and_krakgo.KrakGoApp
import com.kacper.and_krakgo.R
import com.kacper.and_krakgo.helpers.DateHelper
import com.kacper.and_krakgo.helpers.FirebaseDatabaseHelper
import com.kacper.and_krakgo.helpers.GlideHelper
import com.kacper.and_krakgo.model.ForumMessage
import com.kacper.and_krakgo.model.UserDetails
import com.kacper.and_krakgo.mvp.MvpPresenterImpl
import com.kacper.and_krakgo.screens.dialogs.DialogUserInfo

/**
 * Created by kacper on 27/01/2018.
 */
class MessagesAdapter(var messages: ArrayList<ForumMessage>, var listener: RecyclerViewClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent?.context
        return if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ReceivedViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_message_received, parent, false), listener)
        } else {
            SentViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_message_send, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userID.equals(KrakGoApp.getCurrentUser().uid))
            VIEW_TYPE_MESSAGE_SENT
        else
            VIEW_TYPE_MESSAGE_RECEIVED
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val isFirstMessage = !(position != 0
                && messages[position].userID == messages[position - 1].userID)

        when (holder?.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentViewHolder)
                    .bind(messages[position], isFirstMessage)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedViewHolder)
                    .bind(messages[position], isFirstMessage)
        }
    }

    open class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mMessageText: TextView = itemView.findViewById(R.id.tv_message_body)
        val mMessageDate: TextView = itemView.findViewById(R.id.tv_message_time)

        open fun bind(message: ForumMessage, isFirstMessage: Boolean) {
            mMessageText.text = message.message_text
            if (isFirstMessage) {
                mMessageDate.text = DateHelper.getFomattedMessageDate(message.time)
                mMessageDate.visibility = View.VISIBLE

            } else mMessageDate.visibility = View.GONE
        }
    }

    class ReceivedViewHolder(itemView: View, var listener:RecyclerViewClickListener) : SentViewHolder(itemView), View.OnClickListener {
        override fun onClick(view: View?) {
           listener.onClick(view, adapterPosition)
        }

        val mAvatar: ImageView = itemView.findViewById(R.id.civ_message_avatar)
        val mUserName: TextView = itemView.findViewById(R.id.tv_message_name)
        var mMessage: ForumMessage? = null

        override fun bind(message: ForumMessage, isFirstMessage: Boolean) {
            super.bind(message, isFirstMessage)
            mMessage = message
            val params = mMessageText.layoutParams as RelativeLayout.LayoutParams
            val density = KrakGoApp.getApplicationCtx().resources.displayMetrics.density
            var marginLeft = Math.round(48 * density)
            mMessageText.setOnClickListener(this)
            mAvatar.setOnClickListener(this)
            if (isFirstMessage) {
                mAvatar.visibility = View.VISIBLE
                mUserName.visibility = View.VISIBLE
                marginLeft = Math.round(8 * density)
                GlideHelper.load(KrakGoApp.getApplicationCtx(), mAvatar, message.avatarUrl)
                mUserName.text = message.userName
            } else {
                mAvatar.visibility = View.GONE
                mUserName.visibility = View.GONE
            }
            params.setMargins(marginLeft, 0, 0, 0)
            params.marginStart = marginLeft
            mMessageText.layoutParams = params
        }
    }


}