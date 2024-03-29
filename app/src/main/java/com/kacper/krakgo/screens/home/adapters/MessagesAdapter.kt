package com.kacper.krakgo.screens.home.adapters

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.kacper.krakgo.R
import com.kacper.krakgo.helpers.DateHelper
import com.kacper.krakgo.helpers.GlideHelper
import com.kacper.krakgo.model.Message

/**
 * Created by kacper on 27/01/2018.
 */
class MessagesAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    private var mContext: Context? = null
    lateinit var listener: RecyclerViewClickListener
    var messages: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        return if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ReceivedViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_received, parent, false), listener)
        } else {
            SentViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_send, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addListener(listener: RecyclerViewClickListener) {
        this.listener = listener
    }

    fun setData(messages: ArrayList<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].userID.equals(
                        FirebaseAuth.getInstance().currentUser?.uid))
            VIEW_TYPE_MESSAGE_SENT
        else
            VIEW_TYPE_MESSAGE_RECEIVED
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val isFirstMessage = !(position != 0
                && messages[position].userID == messages[position - 1].userID)

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentViewHolder)
                    .bind(messages[position], isFirstMessage)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedViewHolder)
                    .bind(messages[position], isFirstMessage)
        }
    }

    open class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mMessageText: TextView = itemView.findViewById(R.id.tv_message_body)
        private val mMessageDate: TextView = itemView.findViewById(R.id.tv_message_time)

        open fun bind(message: Message, isFirstMessage: Boolean) {
            mMessageText.text = message.message_text
            if (isFirstMessage) {
                mMessageDate.text = DateHelper.getFomattedMessageDate(mMessageDate.context,
                        message.time)
                mMessageDate.visibility = View.VISIBLE

            } else mMessageDate.visibility = View.GONE
        }
    }

    class ReceivedViewHolder(itemView: View, var listener: RecyclerViewClickListener)
        : SentViewHolder(itemView), View.OnClickListener {
        override fun onClick(view: View?) {
            listener.onClick(view, adapterPosition)
        }

        private val mAvatar: ImageView = itemView.findViewById(R.id.civ_message_avatar)
        private val mUserName: TextView = itemView.findViewById(R.id.tv_message_name)
        var mMessage: Message? = null

        override fun bind(message: Message, isFirstMessage: Boolean) {
            super.bind(message, isFirstMessage)
            mMessage = message
            val params = mMessageText.layoutParams as RelativeLayout.LayoutParams
            val density = Resources.getSystem().displayMetrics.density
            var marginLeft = Math.round(48 * density)
            mMessageText.setOnClickListener(this)
            mAvatar.setOnClickListener(this)
            if (isFirstMessage) {
                mAvatar.visibility = View.VISIBLE
                mUserName.visibility = View.VISIBLE
                marginLeft = Math.round(8 * density)
                GlideHelper.load(mAvatar, message.avatarUrl)
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