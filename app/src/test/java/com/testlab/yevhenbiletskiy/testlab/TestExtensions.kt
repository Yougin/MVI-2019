package com.testlab.yevhenbiletskiy.testlab

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

fun <T> TestObserver<T>.getAllEvents() = this.awaitTerminalEvent(20, TimeUnit.MILLISECONDS)