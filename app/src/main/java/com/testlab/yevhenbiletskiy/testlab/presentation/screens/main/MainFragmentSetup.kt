package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.di.viewmodel.ScreenScope
import com.testlab.yevhenbiletskiy.testlab.presentation.di.viewmodel.ViewModelKey
import com.testlab.yevhenbiletskiy.testlab.presentation.di.viewmodel.ViewModelModule
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Subcomponent(modules = [(MainFragmentModule::class)])
@ScreenScope
interface MainFragmentComponent {
    fun inject(activity: MainFragment)
}

@Module(includes = [ViewModelModule::class])
abstract class MainFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindUserViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @ScreenScope
    abstract fun providesProcessor(mainProcessor: MainProcessor): Processor<MainAction, Lce<out MainResult>>

}