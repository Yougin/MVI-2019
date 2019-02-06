package com.testlab.yevhenbiletskiy.testlab

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.testlab.yevhenbiletskiy.testlab.ui.main.MainFragmentComponent
import com.testlab.yevhenbiletskiy.testlab.ui.main.MainViewModel
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Scope
import javax.inject.Singleton
import kotlin.reflect.KClass

@Scope
annotation class MainFragmentScope

@Singleton
@Component(modules = [
  AppModule::class
])
interface AppComponent {
  fun inject(app: App)

  fun getMainFragmentComponent(): MainFragmentComponent
}

@Module
class AppModule(private val app: Context) {
  @Provides
  @Singleton
  fun provideApplicationContext(): Context = app
}
