package com.testlab.yevhenbiletskiy.testlab.ui.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.mvi.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class MainViewModel : ViewModel(), MviViewModel<MainIntent, MainState> {

  private val intentsEmitter = PublishSubject.create<MainIntent>()

  private val _viewStateEmitter = stream()
  override fun viewState(): Observable<MainState> = _viewStateEmitter

  override fun intents(intents: Observable<MainIntent>) {
    intents.subscribe(intentsEmitter)
  }

  private fun stream(): Observable<MainState> {
    return intentsEmitter
        .takeInitialObserverOnlyOnce()
        .doOnNext { Timber.d("Intent: ${it.javaClass.simpleName}") }
        .mapIntentIntoResult()
  }

  private fun Observable<MainIntent>.mapIntentIntoResult(): Observable<MainState> =
      compose(MainProcessor.process)

  private fun Observable<MainIntent>.takeInitialObserverOnlyOnce() =
      compose { upstream ->
        upstream.publish { shared ->
          Observable.merge(
              shared.ofType(MainIntent.InitialIntent::class.java).take(1),
              shared.filter { it !is MainIntent.InitialIntent }
          )
        }
      }
}

