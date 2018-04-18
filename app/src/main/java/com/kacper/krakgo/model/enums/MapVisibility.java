package com.kacper.krakgo.model.enums;

import com.kacper.krakgo.R;

/**
 * Created by kacper on 30/01/2018.
 */

public enum MapVisibility {
    INVISIBLE(R.string.map_status_invisible, R.color.mapStatusInvisible),
    VISIBLE(R.string.map_status_visible, R.color.mapStatusVisible),
    INVITING(R.string.map_status_inviting, R.color.mapStatusInviting);

    private int colourResource;
    private int stringResource;

    private MapVisibility(int stringResource, int colourResource) {
        this.stringResource = stringResource;
        this.colourResource = colourResource;
    }

    public int getColourResource() {
        return colourResource;
    }

    public int getStringResource() {
        return stringResource;
    }
}
