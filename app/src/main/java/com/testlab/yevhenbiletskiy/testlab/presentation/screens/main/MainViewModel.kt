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
    MviViewModel<MainIntent, MainState, MainEffect> {

  private val intentsEmitter = PublishSubject.create<MainIntent>()

  private val _viewState = BehaviorSubject.create<MainState>()
  override fun viewState(): Observable<MainState> = _viewState

  private val _viewEffect = PublishSubject.create<MainEffect>()
  override fun viewEffect(): Observable<MainEffect> = _viewEffect

  private var disposable: Disposable? = null

  init {
    val viewChanges = intentsEmitter
        .takeInitialIntentOnlyOnce()
        .doOnNext { Timber.d("----- Intent: ${it.javaClass.simpleName}") }
        .compose(processor.process())
        .doOnNext { Timber.d("----- Result: ${it.javaClass.simpleName}") }
        .publish()

    // TODO-eugene test view effect too
    viewChanges.compose(resultToViewState()).subscribe(_viewState)
    viewChanges.compose(resultToViewEffect()).subscribe(_viewEffect)

    viewChanges.autoConnect(0) { disposable = it }
  }

  override fun intents(intents: Observable<MainIntent>): Disposable =
      intents.subscribe(
          { intentsEmitter.onNext(it) },
          { Timber.e(it, "Something went wrong processing intents") }
      )

  private fun resultToViewState() =
      ObservableTransformer<Lce<out MainResult>, MainState> { upstream ->
        upstream.scan(MainState.idle(), MainReducer.reduce()).distinctUntilChanged()
      }

  private fun resultToViewEffect() =
      ObservableTransformer<Lce<out MainResult>, MainEffect> { upstream ->
        upstream.publish { shared ->
          shared.filter { it is Lce.Content }
              .cast(Lce.Content::class.java)
              .map<MainEffect> {
                when (it.packet) {
                  is MainResult.InitialLoadResult -> MainEffect.ShowToastEffect("Initial Load completed")
                  else -> throw IllegalStateException("Unknown MainResult type")
                }
              }
        }
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

  override fun onCleared() {
    super.onCleared()
    disposable?.dispose()
  }
}