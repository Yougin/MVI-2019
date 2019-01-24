package com.testlab.yevhenbiletskiy.testlab.ui.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.mvi.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject

class MainViewModel : ViewModel(), MviViewModel<MainIntent, MainState> {

  private val intentsSubject = PublishSubject.create<MainIntent>()
  private val _stateSubject = stream()

  override fun state(): Observable<MainState> = _stateSubject

  override fun intents(intents: Observable<MainIntent>) {
    intents.subscribe(intentsSubject)
  }

  private fun stream(): Observable<MainState> {
    return intentsSubject
        .compose(filterInitialIntent())
        .map { MainState.idle() }
  }

  // TODO-eugene represent it as an extension function
  private fun filterInitialIntent() =
      ObservableTransformer<MainIntent, MainIntent> { upstream ->
        upstream.publish { shared ->
          Observable.merge(
              shared.ofType(MainIntent.InitialIntent::class.java).take(1),
              shared.filter { it !is MainIntent.InitialIntent }
          )
        }
      }

}