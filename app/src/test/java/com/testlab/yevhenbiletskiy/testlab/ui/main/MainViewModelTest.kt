package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.google.common.truth.Truth.assertThat
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.*
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest {

  @Mock private lateinit var processor: MainProcessor
  private lateinit var viewModel: MainViewModel

  @Before fun setUp() {
    MockitoAnnotations.initMocks(this)

    viewModel = MainViewModel(processor)
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

  @Test fun `should emit state only only once for each initial intent`() {
    val observer = viewModel.viewState().test()
    val emitter = PublishSubject.create<MainIntent>()
    viewModel.intents(emitter)

    emitter.onNext(MainIntent.InitialIntent)
    observer.assertValueCount(2)

    emitter.onNext(MainIntent.InitialIntent)
    observer.assertValueCount(2)
  }

  @Test fun `should emit state with text on initial intent`() {
    val observer = viewModel.viewState().test()
    val emitter = PublishSubject.create<MainIntent>()
    viewModel.intents(emitter)

    emitter.onNext(MainIntent.InitialIntent)
    observer.assertValueCount(2)

    assertThat(observer.values()[1]).isEqualTo(
        MainState(
            false,
            "Hello World!"
        )
    )
  }

}