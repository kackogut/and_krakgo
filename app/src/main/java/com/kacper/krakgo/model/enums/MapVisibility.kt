package com.kacper.krakgo.model.enums

import com.kacper.krakgo.R

/**
 * Created by kacper on 30/01/2018.
 */

enum class MapVisibility(val stringResource: Int, val colourResource: Int) {
    INVISIBLE(R.string.map_status_invisible, R.color.mapStatusInvisible),
    VISIBLE(R.string.map_status_visible, R.color.mapStatusVisible),
    INVITING(R.string.map_status_inviting, R.color.mapStatusInviting)
}
