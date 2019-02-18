package com.testlab.yevhenbiletskiy.testlab.domain.main

import arrow.core.Option
import io.reactivex.Observable
import javax.inject.Inject

interface GetMainData {
  // TODO-eugene you should be returning an Option.
  operator fun invoke(): Observable<Option<MainText>>
}

class GetMainDataUseCase @Inject constructor() : GetMainData {

  override operator fun invoke(): Observable<Option<MainText>> {
    return Observable.fromCallable {
      Thread.sleep(3000)
      Option.just(MainText("Hello World, I'm Main Data."))
    }
  }
}
