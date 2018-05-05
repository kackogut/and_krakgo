package com.kacper.krakgo.helpers

import android.content.Context
import android.util.Patterns

import com.kacper.krakgo.model.enums.InputTypes
import com.kacper.krakgo.R

import java.util.regex.Pattern

/**
 * Created by kacper on 22/10/2017.
 */

object ValidationHelper {
    private val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
    private val EMAIL_PATTERN = Patterns.EMAIL_ADDRESS

    fun validateText(context: Context, text: String, inputType: InputTypes): String? {
        if (text.length > inputType.getmMaximumLength())
            return context.getString(R.string.error_max_characters_placeholder, inputType.getmMaximumLength())
        else if (text.length < inputType.getmMinimumLength())
            return context.getString(R.string.error_minimum_character_placeholder, inputType.getmMinimumLength())
        else if (!getPatternByType(inputType).matcher(text).matches())
            return context.getString(getMessageByType(inputType))
        return null
    }

    private fun getPatternByType(type: InputTypes): Pattern {
        when (type) {
            InputTypes.PASSWORD, InputTypes.RETYPEPASSWORD -> return Pattern.compile(PASSWORD_PATTERN)
            InputTypes.EMAIL -> return EMAIL_PATTERN
            else -> return EMAIL_PATTERN
        }
    }

    private fun getMessageByType(type: InputTypes): Int {
        when (type) {
            InputTypes.PASSWORD, InputTypes.RETYPEPASSWORD -> return R.string.password_field_error
            InputTypes.EMAIL -> return R.string.error_email_field
            else -> return R.string.error_default
        }
    }
}
