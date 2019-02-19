package com.testlab.yevhenbiletskiy.testlab.ui.main

import arrow.core.Option
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.testlab.yevhenbiletskiy.testlab.domain.main.GetMainData
import com.testlab.yevhenbiletskiy.testlab.domain.main.MainText
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainIntent
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainProcessor
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainState
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class MainViewModelTest {

  private lateinit var viewModel: MainViewModel
  private lateinit var processor: MainProcessor

  private var getMainData = mock<GetMainData>()

  @Before fun setUp() {
    assumeGetMainData(returns = "test")
    processor = MainProcessor(getMainData)
    viewModel = MainViewModel(processor)
  }

  private fun assumeGetMainData(returns: String) {
    whenever(getMainData.invoke()).thenReturn(Observable.just(Option.just(MainText(returns))))
  }

  @Test fun `should emit state with loading true on initial intent`() {
    val observer = viewModel.viewState().test()
    val emitter = PublishSubject.create<MainIntent>()
    viewModel.intents(emitter)

    emitter.onNext(MainIntent.InitialIntent)

    val values = observer.values()
    assertThat(values[0]).isEqualTo(
        MainState(
            isLoading = true,
            text = ""
        )
    )
  }

  @Test fun `should emit state only once for each initial intent`() {
    val observer = viewModel.viewState().test()
    val emitter = PublishSubject.create<MainIntent>()
    viewModel.intents(emitter)

    emitter.onNext(MainIntent.InitialIntent)
    observer.awaitTerminalEvent(20, TimeUnit.MILLISECONDS)

    observer.assertValueCount(2)

    emitter.onNext(MainIntent.InitialIntent)
    observer.awaitTerminalEvent(20, TimeUnit.MILLISECONDS)

    observer.assertValueCount(2)
  }

  @Test fun `should emit state with text on initial intent`() {
    val observer = viewModel.viewState().test()
    val emitter = PublishSubject.create<MainIntent>()
    viewModel.intents(emitter)

    emitter.onNext(MainIntent.InitialIntent)
    observer.awaitTerminalEvent(20, TimeUnit.MILLISECONDS)

    observer.assertValueCount(2)

    assertThat(observer.values()[1]).isEqualTo(MainState(false, "test"))
  }

}