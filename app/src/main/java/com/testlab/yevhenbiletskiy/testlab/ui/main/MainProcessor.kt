package com.testlab.yevhenbiletskiy.testlab.ui.main

import io.reactivex.ObservableTransformer

object MainProcessor {
  val process = ObservableTransformer<MainAction, MainResult.InitialLoadResult> { upstream ->
    upstream.publish { shared ->
      shared.ofType(MainAction.InitialLoadAction::class.java).compose { it.map { MainResult.InitialLoadResult("afd") } }
    }
  }
}