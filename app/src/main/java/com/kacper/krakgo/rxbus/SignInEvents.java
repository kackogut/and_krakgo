package com.kacper.krakgo.rxbus;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kacper on 12/11/2017.
 */

public class SignInEvents {
    public static final int ACTION_FORGOT_PASSSWORD_CLICKED = 3;
    public static final int ACTION_BOTTOM_BUTTON_REGISTER_1 = 4;
    public static final int ACTION_BOTTOM_BUTTON_REGISTER_2 = 5;
    public static final int ACTION_BOTTOM_BUTTON_PASSWORD = 6;

    private static PublishSubject<Integer> sInstance;

    private static PublishSubject<Integer> getInstance(){
        if(sInstance == null)
            sInstance = PublishSubject.create();
        return sInstance;
    }

    public static Disposable subscribe(@NonNull Consumer<Integer> action){
        return getInstance().subscribe(action);
    }

    public static void publish(@NonNull Integer action){
        getInstance().onNext(action);
    }
}
