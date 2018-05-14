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
    private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
    private val EMAIL_PATTERN = Patterns.EMAIL_ADDRESS

    fun validateText(context: Context?, text: String, inputType: InputTypes): String? {
        if (text.length > inputType.getMaximumLength())
            return context?.getString(R.string.error_max_characters_placeholder, inputType.getMaximumLength())
        else if (text.length < inputType.getMinimumLength())
            return context?.getString(R.string.error_minimum_character_placeholder, inputType.getMinimumLength())
        else if (!getPatternByType(inputType).matcher(text).matches())
            return context?.getString(getMessageByType(inputType))
        return null
    }

    private fun getPatternByType(type: InputTypes): Pattern {
        return when (type) {
            InputTypes.PASSWORD, InputTypes.RETYPEPASSWORD -> Pattern.compile(PASSWORD_PATTERN)
            InputTypes.EMAIL -> EMAIL_PATTERN
            else -> EMAIL_PATTERN
        }
    }

    private fun getMessageByType(type: InputTypes): Int {
        return when (type) {
            InputTypes.PASSWORD,
            InputTypes.RETYPEPASSWORD -> R.string.password_field_error
            InputTypes.EMAIL -> R.string.error_email_field
            else -> R.string.error_default
        }
    }
}
