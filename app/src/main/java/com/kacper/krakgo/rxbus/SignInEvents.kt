package com.kacper.krakgo.rxbus

import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by kacper on 12/11/2017.
 */

object SignInEvents {
    const val ACTION_FORGOT_PASSSWORD_CLICKED = 3
    const val ACTION_BOTTOM_BUTTON_REGISTER_1 = 4
    const val ACTION_BOTTOM_BUTTON_REGISTER_2 = 5
    const val ACTION_BOTTOM_BUTTON_PASSWORD = 6

    private var sInstance: PublishSubject<Int>? = null

    private val instance: PublishSubject<Int>
        get() {
            if (sInstance == null)
                sInstance = PublishSubject.create()
            return sInstance!!
        }

    fun subscribe(@NonNull action: (Any) -> Unit): Disposable {
        return instance.subscribe(action)
    }

    fun publish(@NonNull action: Int) {
        instance.onNext(action)
    }
}
