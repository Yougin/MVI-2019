@file:Suppress("PropertyName")

package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainEffect
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect> : ViewModel() {

  protected val intentsEmitter = PublishSubject.create<I>()

  protected val _viewState = BehaviorSubject.create<S>()
  fun viewState(): Observable<S> = _viewState

  protected val _viewEffect = PublishSubject.create<E>()
  fun viewEffect(): Observable<E> = _viewEffect

  fun intents(intents: Observable<I>): Disposable =
      intents.subscribe(
          { intentsEmitter.onNext(it) },
          { Timber.e(it, "Something went wrong processing intents") }
      )

  protected var disposable: Disposable? = null
  override fun onCleared() {
    super.onCleared()
    disposable?.dispose()
  }
}