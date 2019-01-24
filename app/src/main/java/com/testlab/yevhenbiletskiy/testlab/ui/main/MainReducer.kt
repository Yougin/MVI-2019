package com.testlab.yevhenbiletskiy.testlab.ui.main

import io.reactivex.functions.BiFunction

object MainReducer {
  fun reduce(): BiFunction<MainState, MainResult, MainState> = BiFunction { viewState, result ->
    MainState.idle()
  }
}