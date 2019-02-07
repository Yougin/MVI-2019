package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import io.reactivex.Observable

interface MviViewModel<I : MviIntent, S : MviState> {

  fun intents(intents: Observable<I>)

  fun viewState(): Observable<S>
}