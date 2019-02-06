package com.testlab.yevhenbiletskiy.testlab

import android.content.Context
import com.testlab.yevhenbiletskiy.testlab.ui.main.MainFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton

@Scope
annotation class MainFragmentScope

@Singleton
@Component(modules = [(AppModule::class)])
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

@Subcomponent(modules = [(MainFragmentModule::class)])
interface MainFragmentComponent {
  fun inject(activity: MainFragment)
}

@Module()
class MainFragmentModule