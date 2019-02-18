package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.domain.main.GetMainData
import com.testlab.yevhenbiletskiy.testlab.domain.main.MainText
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainProcessor @Inject constructor(
    private val getMainData: GetMainData
) : Processor<MainIntent, Lce<out MainResult>> {

    override fun process() = ObservableTransformer<MainIntent, Lce<out MainResult>> { upstream ->
        upstream.publish<Lce<out MainResult>> { shared ->
            shared.ofType(MainIntent.InitialIntent::class.java).onFetchDataAction()
        }
    }

    private fun Observable<out MainIntent>.onFetchDataAction(): Observable<Lce<out MainResult>> =
        flatMap {
            getMainData()
                .map<Lce<MainResult>> { Lce.Content(MainResult.InitialLoadResult(MainText(it))) }
                .startWith(Lce.Loading())
                .subscribeOn(Schedulers.io())
        }
}

