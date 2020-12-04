package com.abrahamlay.core.subscriber

import io.reactivex.observers.DisposableCompletableObserver

abstract class DefaultCompletableObserver : DisposableCompletableObserver() {
    override fun onError(throwable: Throwable) {
        onError(ResultState.Error(throwable))
    }

    abstract fun onError(error: ResultState<Throwable>)

}