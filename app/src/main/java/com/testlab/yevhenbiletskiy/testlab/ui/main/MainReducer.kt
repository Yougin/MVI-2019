package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import io.reactivex.functions.BiFunction

object MainReducer {
  fun reduce(): BiFunction<MainState, Lce<out MainResult>, MainState> =
      BiFunction { viewState, result ->
        MainState.idle()
      }
}