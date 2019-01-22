package com.testlab.yevhenbiletskiy.testlab.mvi

import io.reactivex.Observable

interface MviViewModel<I : MviIntent, S : MviState> {

    fun intents(intents: Observable<I>)

    fun state(): Observable<S>
}