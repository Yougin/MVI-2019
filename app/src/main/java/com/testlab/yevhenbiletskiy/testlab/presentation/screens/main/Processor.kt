package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import io.reactivex.ObservableTransformer

interface Processor<T, U> {
  fun process(): ObservableTransformer<T, U>
}