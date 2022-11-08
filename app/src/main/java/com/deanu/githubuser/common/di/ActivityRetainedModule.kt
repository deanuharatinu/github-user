package com.deanu.githubuser.common.di

import com.deanu.githubuser.common.data.GithubUserRepoImpl
import com.deanu.githubuser.common.domain.repository.GithubUserRepository
import com.deanu.githubuser.common.utils.coroutine.CoroutineDispatchersProvider
import com.deanu.githubuser.common.utils.coroutine.DispatchersProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
  @Binds
  @ActivityRetainedScoped
  abstract fun bindsGithubUserRepo(githubUserRepoImpl: GithubUserRepoImpl): GithubUserRepository

  @Binds
  abstract fun bindsDispatcherProvider(dispatchersProvider: CoroutineDispatchersProvider): DispatchersProvider
}