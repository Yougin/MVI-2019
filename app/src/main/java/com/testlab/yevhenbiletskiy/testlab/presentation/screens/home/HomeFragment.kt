package com.testlab.yevhenbiletskiy.testlab.presentation.screens.home

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeFragment : Fragment() {

  private val disposables = CompositeDisposable()

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: HomeViewModel

  
}