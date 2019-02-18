package com.testlab.yevhenbiletskiy.testlab.domain.main

import io.reactivex.Observable
import javax.inject.Inject

inline class MainText(val value: String)

class GetMainData @Inject constructor() {
    operator fun invoke(): Observable<String> {
        return Observable.fromCallable {
            Thread.sleep(3000)
            "Hello World, I'm Main Data."
        }
    }
}
