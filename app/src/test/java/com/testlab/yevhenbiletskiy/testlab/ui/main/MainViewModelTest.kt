package com.testlab.yevhenbiletskiy.testlab.ui.main

import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

  private lateinit var viewModel: MainViewModel

  @Before fun setUp() {
    viewModel = MainViewModel()
  }

  @Test fun `should emit idle status on initial intent`() {
    val observer = viewModel.viewState().test()

    val emitter = PublishSubject.create<MainIntent>()
    viewModel.intents(emitter)
    emitter.onNext(MainIntent.InitialIntent)

    observer.assertValue { it == MainState.idle() }
  }

  //  @Test fun `should emit initial intent only once`(){
  //
  //  }
}