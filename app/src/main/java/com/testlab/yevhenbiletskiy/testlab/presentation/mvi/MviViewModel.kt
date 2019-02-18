package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface MviViewModel<I : MviIntent, S : MviState, E : MviEffect> {

  fun intents(intents: Observable<I>): Disposable

  fun viewState(): Observable<S>

  fun viewEffect(): Observable<E>
}