package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val processor: Processor<MainAction, Lce<out MainResult>>
) : ViewModel(),
    MviViewModel<MainIntent, MainState> {

  private val intentsEmitter = PublishSubject.create<MainIntent>()

  private val _viewStateEmitter = stream()
  override fun viewState(): Observable<MainState> = _viewStateEmitter

  override fun intents(intents: Observable<MainIntent>) {
    intents.subscribe(intentsEmitter)
  }

  private fun stream() = intentsEmitter
      .takeInitialIntentOnlyOnce()
      .doOnNext { Timber.d("----- Intent: ${it.javaClass.simpleName}") }
      .map { intentIntoActions(it) }
      .compose(processor.process())
      .doOnNext { Timber.d("----- Result: ${it.javaClass.simpleName}") }
      .scan(MainState.idle(), MainReducer.reduce())
      .distinctUntilChanged()
      .replay(1)
      .autoConnect(0)

  private fun intentIntoActions(it: MainIntent): MainAction =
      when (it) {
        MainIntent.InitialIntent -> MainAction.FetchDataAction
      }

  private fun Observable<MainIntent>.takeInitialIntentOnlyOnce() =
      compose { upstream ->
        upstream.publish { shared ->
          Observable.merge(
              shared.ofType(MainIntent.InitialIntent::class.java).take(1),
              shared.filter { it !is MainIntent.InitialIntent }
          )
        }
      }
}
