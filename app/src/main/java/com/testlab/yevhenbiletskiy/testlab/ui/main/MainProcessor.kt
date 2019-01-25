package com.testlab.yevhenbiletskiy.testlab.ui.main

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

object MainProcessor {
  val process = ObservableTransformer<MainAction, MainResult> { upstream ->
    upstream.publish { shared ->
      shared.ofType(MainAction.FetchDataAction::class.java).onFetchDataAction()
    }
  }

  private fun Observable<out MainAction>.onFetchDataAction(): Observable<MainResult> {
    return map { MainResult.InitialLoadResult("Hello World!") }
  }

}