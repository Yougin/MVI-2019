package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect> : ViewModel() {

  protected val intentsEmitter = PublishSubject.create<I>()

  protected val _viewState = BehaviorSubject.create<MainState>()
  fun viewState(): Observable<MainState> = _viewState

  abstract fun viewEffect(): Observable<E>

  fun intents(intents: Observable<I>): Disposable =
      intents.subscribe(
          { intentsEmitter.onNext(it) },
          { Timber.e(it, "Something went wrong processing intents") }
      )
}