package com.example.core.base

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/*
* simple replacement for RxRelay
* */
class RxBus<in T> {

    private val subject = PublishSubject.create<T>().toSerialized()

    fun<E: T> send(event: E) {
        subject.onNext(event)
    }

    fun send(throwable: Throwable) {
        subject.onError(throwable)
    }

    fun<E: T> observeEvents(eventClass: Class<E>): Observable<E> {
        return subject.ofType(eventClass)
    }
}