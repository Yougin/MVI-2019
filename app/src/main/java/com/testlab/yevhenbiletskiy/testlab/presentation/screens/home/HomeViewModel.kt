package com.testlab.yevhenbiletskiy.testlab.presentation.screens.home

import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) : MviViewModel<HomeIntent, HomeState, HomeEffect>()