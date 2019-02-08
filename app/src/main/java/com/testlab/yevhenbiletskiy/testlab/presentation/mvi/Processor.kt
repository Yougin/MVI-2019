package com.testlab.yevhenbiletskiy.testlab.presentation.mvi

import io.reactivex.ObservableTransformer

interface Processor<T, U> {
  fun process(): ObservableTransformer<T, U>
}