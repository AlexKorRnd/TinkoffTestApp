package com.example.core.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposablePresenter {

    protected val disposables = CompositeDisposable()

    operator fun CompositeDisposable.plusAssign(subscription: Disposable) {
        add(subscription)
    }

    fun stop() {
        disposables.clear()
    }
}