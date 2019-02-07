package com.testlab.yevhenbiletskiy.testlab.presentation.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

// View Model injection part
// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java /com/android/example/github/di/ViewModelModule.kt
// https://www.techyourchance.com/dependency-injection-viewmodel-with-dagger-2/

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
