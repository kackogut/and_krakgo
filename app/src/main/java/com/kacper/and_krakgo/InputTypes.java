package com.kacper.and_krakgo;

/**
 * Created by kacper on 22/10/2017.
 */

public enum InputTypes {
    PASSWORD(6,30),
    EMAIL(4,70),
    NAME(1, 70),
    SURNAME(1,70),
    RETYPEPASSWORD(6,30);

    private int mMinimumLength;
    private int mMaximumLength;

    InputTypes(int mMinimumLength, int mMaximumLength) {
        this.mMinimumLength = mMinimumLength;
        this.mMaximumLength = mMaximumLength;
    }

    public int getmMinimumLength() {
        return mMinimumLength;
    }

    public int getmMaximumLength() {
        return mMaximumLength;
    }
}
