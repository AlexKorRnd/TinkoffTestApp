package com.example.alexkorrnd.tinkofftestapp.di.app

import androidx.annotation.NonNull
import com.example.alexkorrnd.tinkofftestapp.MainApp
import dagger.Component
import dagger.internal.Preconditions
import javax.inject.Singleton

@Component(modules = [
    GlobalNavigationModule::class,
    AppModule::class]
)
@Singleton
abstract class AppComponent {

    abstract fun inject(daggerArchApplication: MainApp)

    abstract fun mainScreenComponent(): MainScreenComponent

    companion object {
        @Volatile
        private var sInstance: AppComponent? = null

        @NonNull
        fun get(): AppComponent {
            return Preconditions.checkNotNull(
                sInstance!!,
                "AppComponent is not initialized yet. Call init first."
            )
        }

        fun init(@NonNull component: AppComponent) {
            if (sInstance != null) {
                throw IllegalArgumentException("AppComponent is already initialized.")
            }
            sInstance = component
        }
    }

}