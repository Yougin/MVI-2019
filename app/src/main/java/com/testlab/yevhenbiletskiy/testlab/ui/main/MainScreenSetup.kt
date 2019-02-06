package com.testlab.yevhenbiletskiy.testlab.ui.main

import dagger.Module
import dagger.Subcomponent

@Subcomponent(modules = [(MainFragmentModule::class)])
interface MainFragmentComponent {
  fun inject(activity: MainFragment)
}

@Module()
class MainFragmentModule