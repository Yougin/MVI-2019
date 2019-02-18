package com.testlab.yevhenbiletskiy.testlab.domain.main

import io.reactivex.Observable
import javax.inject.Inject

interface GetMainData {
  // TODO-eugene you should be returning an Option.
  operator fun invoke(): Observable<MainText>
}

class GetMainDataUseCase @Inject constructor() : GetMainData {

  override operator fun invoke(): Observable<MainText> {
    return Observable.fromCallable {
      Thread.sleep(3000)
      MainText("Hello World, I'm Main Data.")
    }
  }
}
