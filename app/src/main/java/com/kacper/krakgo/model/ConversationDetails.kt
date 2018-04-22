package com.kacper.krakgo.model

import io.reactivex.annotations.Nullable

/**
 * Created by kacper on 07/02/2018.
 */

class ConversationDetails(val conversationID: String,
                          val user_avatar: String,
                          val user_name: String,
                          val time: Long) {

    @Nullable
    var user_id: String? = null

    constructor() : this("","","", -1) {
        //Constructor needed for firebase data parse
    }
}
