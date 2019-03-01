@file:Suppress("PropertyName")

package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.utils.takeOnlyOnce
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class BaseViewModel<I : Intent, R : Result, S : ViewState, E : ViewEffect>
constructor(
    processor: Processor<I, Lce<out R>>
) : ViewModel() {

  /** Use to supply Intents from View **/
  fun intents(intents: Observable<I>): Disposable {
    createPipeline
    return intents.subscribe(
        { intentsEmitter.onNext(it) },
        { Timber.e(it, "Something went wrong processing intents") }
    )
  }

  /** Returns an Observable which emits recent ViewState **/
  fun viewState(): Observable<S> = _viewState

  /** Returns an Observable which emits ViewEffect **/
  fun viewEffect(): Observable<E> = _viewEffect

  /** Implement to return a Reducer **/
  protected abstract val reducer: BiFunction<S, Lce<out R>, S>

  /** Implement to get a default/initial view state **/
  protected abstract val defaultState: S

  /** Implement to get an Initial Intent **/
  protected abstract val initialIntent: Class<out I>

  /** Implement to translate results to View ViewEffect **/
  protected abstract fun resultToViewEffect(): ObservableTransformer<Lce<out R>, E>



  private val intentsEmitter = PublishSubject.create<I>()
  private val _viewState = BehaviorSubject.create<S>()
  private val _viewEffect = PublishSubject.create<E>()
  private var disposable: Disposable? = null

  private val createPipeline by lazy {
    val viewChanges = intentsEmitter
        .takeOnlyOnce(initialIntent)
        .doOnNext { Timber.d("----- Intent: ${it.javaClass.simpleName}") }
        .compose(processor.process())
        .doOnNext { Timber.d("----- Result: ${it.javaClass.simpleName}") }
        .publish()

    // TODO-eugene test view effect too
    viewChanges.compose(resultToViewState()).subscribe(_viewState)
    viewChanges.compose(resultToViewEffect()).subscribe(_viewEffect)

    viewChanges.autoConnect(0) { disposable = it }
  }

  private fun resultToViewState() =
      ObservableTransformer<Lce<out R>, S> {
        it.scan(defaultState, reducer).distinctUntilChanged()
      }

  override fun onCleared() {
    super.onCleared()
    disposable?.dispose()
  }

}