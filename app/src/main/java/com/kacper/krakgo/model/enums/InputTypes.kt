package com.kacper.krakgo.model.enums

/**
 * Created by kacper on 22/10/2017.
 */

enum class InputTypes(private val mMinimumLength: Int, private val mMaximumLength: Int) {
    PASSWORD(6, 30),
    EMAIL(4, 70),
    NAME(1, 70),
    SURNAME(1, 70),
    RETYPEPASSWORD(6, 30);

    fun getMinimumLength(): Int {
        return mMinimumLength
    }

    fun getMaximumLength(): Int {
        return mMaximumLength
    }
}
