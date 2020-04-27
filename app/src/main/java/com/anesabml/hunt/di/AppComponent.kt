package com.anesabml.hunt.di

import android.content.Context
import com.anesabml.hunt.viewModel.auth.AuthenticationActivity
import com.anesabml.hunt.viewModel.auth.AuthenticationViewModel
import com.anesabml.hunt.viewModel.main.MainActivity
import com.anesabml.hunt.viewModel.postDetails.PostDetailsFragment
import com.anesabml.hunt.viewModel.postDetails.PostDetailsViewModel
import com.anesabml.hunt.viewModel.posts.PostsListFragment
import com.anesabml.hunt.viewModel.posts.PostsListViewModel
import com.anesabml.hunt.viewModel.splash.SplashActivity
import com.anesabml.hunt.viewModel.splash.SplashViewModel
import com.anesabml.hunt.utils.SharedPref
import com.anesabml.hunt.worker.AppWorkerFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, AssistedInjectModule::class, WorkerBindingModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance @Named("token")
            token: String
        ): AppComponent
    }

    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(authenticationActivity: AuthenticationActivity)
    fun inject(postsListFragment: PostsListFragment)
    fun inject(postDetailsFragment: PostDetailsFragment)

    fun workerFactory(): AppWorkerFactory

    val sharedPref: SharedPref

    val splashViewModel: SplashViewModel.Factory
    val authenticationViewModel: AuthenticationViewModel.Factory
    val postDetailsViewModel: PostDetailsViewModel.Factory
    val postsListViewModel: PostsListViewModel.Factory
}
