package com.testlab.yevhenbiletskiy.testlab.ui.main

import arrow.core.Option
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.testlab.yevhenbiletskiy.testlab.domain.main.GetMainData
import com.testlab.yevhenbiletskiy.testlab.domain.main.MainText
import com.testlab.yevhenbiletskiy.testlab.getAllEvents
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainIntent
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainProcessor
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainState
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainViewModel
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableError
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

  private lateinit var viewModel: MainViewModel
  private lateinit var processor: MainProcessor

  private var getMainData = mock<GetMainData>()

  private lateinit var emitter: PublishSubject<MainIntent>
  private lateinit var observer: TestObserver<MainState>

  @Before fun setUp() {
    processor = MainProcessor(getMainData)
    viewModel = MainViewModel(processor)

    observer = viewModel.viewState().test()
    emitter = PublishSubject.create()
    viewModel.intents(emitter)

    assumeGetMainData(returns = Option.just(MainText("something")))
  }

  private fun assumeGetMainData(returns: Option<MainText>) {
    whenever(getMainData.invoke()).thenReturn(Observable.just(returns))
  }

  @Test fun `should emit state with loading true on initial intent`() {
    emitter.onNext(MainIntent.InitialIntent)

    val values = observer.values()
    assertThat(values[0]).isEqualTo(
        MainState(
            isLoading = true,
            text = ""
        )
    )
  }

  @Test fun `should ignore initial intent after the first one`() {
    emitter.onNext(MainIntent.InitialIntent)
    observer.getAllEvents()

    observer.assertValueCount(2)

    emitter.onNext(MainIntent.InitialIntent)
    observer.getAllEvents()

    observer.assertValueCount(2)
  }

  @Test fun `should emit state with text on initial intent`() {
    assumeGetMainData(returns = Option.just(MainText("test")))

    emitter.onNext(MainIntent.InitialIntent)
    observer.getAllEvents()

    observer.assertValueCount(2)

    assertThat(observer.values()[1]).isEqualTo(MainState(false, "test"))
  }

  @Test fun `should emit state with empty text if no main text available`() {
    assumeGetMainData(returns = Option.empty())

    emitter.onNext(MainIntent.InitialIntent)
    observer.getAllEvents()

    observer.assertValueCount(2)
    assertThat(observer.values()[1]).isEqualTo(MainState(false, ""))
  }

}