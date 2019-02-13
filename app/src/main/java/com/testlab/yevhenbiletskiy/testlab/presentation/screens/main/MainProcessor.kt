package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainProcessor @Inject constructor() : Processor<MainAction, Lce<out MainResult>> {

    override fun process() = ObservableTransformer<MainAction, Lce<out MainResult>> { upstream ->
        upstream.publish<Lce<out MainResult>> { shared ->
            shared.ofType(MainAction.FetchDataAction::class.java).onFetchDataAction()
        }
    }

    private fun Observable<out MainAction>.onFetchDataAction(): Observable<Lce<out MainResult>> =
        flatMap {
            Observable
                .just<Lce<MainResult.InitialLoadResult>>(Lce.Content(MainResult.InitialLoadResult("Hello World!")))
                .delay(3, TimeUnit.SECONDS)
                .startWith(Lce.Loading())
        }
}