package com.kacper.and_krakgo.helpers;

import android.content.Context;
import android.util.Patterns;

import com.kacper.and_krakgo.InputTypes;
import com.kacper.and_krakgo.R;

import java.util.regex.Pattern;

/**
 * Created by kacper on 22/10/2017.
 */

public class ValidationHelper {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;

    public static String validateText(Context context, String text, InputTypes inputType){
        if(text.length() > inputType.getmMaximumLength())
            return context.getString(R.string.error_max_characters_placeholder, inputType.getmMaximumLength());
        else if(text.length() < inputType.getmMinimumLength())
            return context.getString(R.string.error_minimum_character_placeholder, inputType.getmMinimumLength());
        else if(!getPatternByType(inputType).matcher(text).matches())
            return context.getString(getMessageByType(inputType));
        return null;
    }
    public static Pattern getPatternByType(InputTypes type){
        switch (type){
            case PASSWORD:
            case RETYPEPASSWORD:
                return Pattern.compile(PASSWORD_PATTERN);
            default:
            case EMAIL:
                return EMAIL_PATTERN;
        }
    }
    public static int getMessageByType(InputTypes type){
        switch (type){
            case PASSWORD:
            case RETYPEPASSWORD:
                return R.string.password_field_error;
            case EMAIL:
                return R.string.error_email_field;
            default:
                return R.string.error_default;
        }
    }
}
