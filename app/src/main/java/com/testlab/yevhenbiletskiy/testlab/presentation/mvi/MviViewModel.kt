package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect> : ViewModel() {

  protected val intentsEmitter = PublishSubject.create<I>()

  abstract fun viewState(): Observable<S>

  abstract fun viewEffect(): Observable<E>

  fun intents(intents: Observable<I>): Disposable =
      intents.subscribe(
          { intentsEmitter.onNext(it) },
          { Timber.e(it, "Something went wrong processing intents") }
      )
}