package com.testlab.yevhenbiletskiy.testlab.ui.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.mvi.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class MainViewModel : ViewModel(), MviViewModel<MainIntent, MainState> {

  private val intentsSubject = PublishSubject.create<MainIntent>()
  private val _stateSubject = stream()

  override fun viewState(): Observable<MainState> = _stateSubject

  override fun intents(intents: Observable<MainIntent>) {
    intents.subscribe(intentsSubject)
  }

  private fun stream(): Observable<MainState> {
    return intentsSubject
        .takeInitialObserverOnlyOnce()
        .doOnNext { Timber.d("Intent: ${it.javaClass.simpleName}") }
        .compose(MainProcessor.process)
  }

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

