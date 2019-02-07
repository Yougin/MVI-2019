package com.testlab.yevhenbiletskiy.testlab.presentation.di.viewmodel

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

// View Model injection part
// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java /com/android/example/github/di/ViewModelModule.kt
// https://www.techyourchance.com/dependency-injection-viewmodel-with-dagger-2/

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
