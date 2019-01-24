package com.testlab.yevhenbiletskiy.testlab.ui.main

import io.reactivex.ObservableTransformer

object MainProcessor {
  val process = ObservableTransformer<MainIntent, MainState> { upstream ->
    upstream.publish { shared ->
      shared.ofType(MainIntent.InitialIntent::class.java).compose { it.map { MainState.idle() } }
    }
  }
}