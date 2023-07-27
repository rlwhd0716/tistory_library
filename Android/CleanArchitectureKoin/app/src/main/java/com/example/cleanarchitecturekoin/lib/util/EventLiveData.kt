package com.example.cleanarchitecturekoin.lib.util

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class EventLiveData<T>(private var onEventUnhandledContent: ((T) -> Unit)? = null) : MediatorLiveData<Event<T>>(), Observer<Event<T>> {
    override fun onChanged(value: Event<T>) {
        onEventUnhandledContent?.let { EventObserver(it).onChanged(value) }
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in Event<T>>) {
        super.observe(owner) { t -> observer.onChanged(t) }
    }

    @MainThread
    fun observe(owner: LifecycleOwner, obsv: ((T) -> Unit)? = null) {
        observe(owner, this.apply { obsv?.let { onEventUnhandledContent = it } })
    }

    @MainThread
    fun setEvent(t: T) = super.setValue(Event(t))

    fun postEvent(t: T) = postValue(Event(t))
}

sealed class Empty {
    object Call: Empty()
}