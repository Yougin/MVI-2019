package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    processor: Processor<MainIntent, Lce<out MainResult>>
) : ViewModel(),
    MviViewModel<MainIntent, MainState> {

  private val intentsEmitter = PublishSubject.create<MainIntent>()
  private val _viewState = BehaviorSubject.create<MainState>()
  override fun viewState(): Observable<MainState> = _viewState

  private var disposable: Disposable? = null

  init {
    val viewChanges = intentsEmitter
        .takeInitialIntentOnlyOnce()
        .doOnNext { Timber.d("----- Intent: ${it.javaClass.simpleName}") }
        .compose(processor.process())
        .doOnNext { Timber.d("----- Result: ${it.javaClass.simpleName}") }
        .publish()

    viewChanges.compose(resultToViewState()).subscribe(_viewState)

    viewChanges.autoConnect(0) { disposable = it }
  }

  private fun resultToViewState() =
      ObservableTransformer<Lce<out MainResult>, MainState> { upstream ->
        upstream.scan(MainState.idle(), MainReducer.reduce()).distinctUntilChanged()
      }

  override fun intents(intents: Observable<MainIntent>): Disposable =
      intents.subscribe(
          { intentsEmitter.onNext(it) },
          { Timber.e(it, "Something went wrong processing intents") }
      )


  private fun Observable<MainIntent>.takeInitialIntentOnlyOnce() =
      compose { upstream ->
        upstream.publish { shared ->
          Observable.merge(
              shared.ofType(MainIntent.InitialIntent::class.java).take(1),
              shared.filter { it !is MainIntent.InitialIntent }
          )
        }
      }

  override fun onCleared() {
    super.onCleared()
    disposable?.dispose()
  }
}