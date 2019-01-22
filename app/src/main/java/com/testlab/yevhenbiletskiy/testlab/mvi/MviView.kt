package com.testlab.yevhenbiletskiy.testlab.mvi

import io.reactivex.Observable

interface MviView<I : MviIntent, S : MviState> {

    fun intents(intents: Observable<I>)

    fun state(): Observable<S>
}