package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

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

// TODO-eugene consider moving things up like Roxie does
class MainViewModel @Inject constructor(
    processor: Processor<MainIntent, Lce<out MainResult>>
) : MviViewModel<MainIntent, MainState, MainEffect>() {

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

  // TODO-eugene Kaushik does viewState.value ?: MSMovieViewState()
  private fun resultToViewState() =
      ObservableTransformer<Lce<out MainResult>, MainState> { upstream ->
        upstream.scan(MainState.idle(), mainReducer()).distinctUntilChanged()
      }

  // TODO-eugene show Another Toast for button click
  private fun resultToViewEffect() =
      ObservableTransformer<Lce<out MainResult>, MainEffect> { upstream ->
        upstream.publish { shared ->
          shared.filter { it is Lce.Content && it.packet is MainResult.InitialLoadResult }
              .cast(Lce.Content::class.java)
              .map<MainEffect> { MainEffect.ShowToastEffect("Initial Load completed") }
        }
      }


  // TODO-eugene generalize for reuse?
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