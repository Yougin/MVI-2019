@file:Suppress("PropertyName")

package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.utils.takeOnlyOnce
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class MviViewModel<I : MviIntent, S : MviState, E : MviEffect, R : MviResult>
constructor(
    processor: Processor<I, Lce<out R>>,
    initialIntent: Class<I>
) : ViewModel() {

  private val intentsEmitter = PublishSubject.create<I>()

  private val _viewState = BehaviorSubject.create<S>()
  fun viewState(): Observable<S> = _viewState

  private val _viewEffect = PublishSubject.create<E>()
  fun viewEffect(): Observable<E> = _viewEffect

  init {
    val viewChanges = intentsEmitter
        .takeOnlyOnce(initialIntent)
        .doOnNext { Timber.d("----- Intent: ${it.javaClass.simpleName}") }
        .compose(processor.process())
        .doOnNext { Timber.d("----- MviResult: ${it.javaClass.simpleName}") }
        .publish()

    // TODO-eugene test view effect too
//        viewChanges.compose(resultToViewState()).subscribe(_viewState)
//        viewChanges.compose(resultToViewEffect()).subscribe(_viewEffect)

    viewChanges.autoConnect(0) { disposable = it }
  }


  fun intents(intents: Observable<I>): Disposable =
      intents.subscribe(
          { intentsEmitter.onNext(it) },
          { Timber.e(it, "Something went wrong processing intents") }
      )

  private var disposable: Disposable? = null
  override fun onCleared() {
    super.onCleared()
    disposable?.dispose()
  }
}
