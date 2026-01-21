package com.emoulgen.jobtrackerapp.dependencyInjection

import android.content.Context
import com.emoulgen.jobtrackerapp.data.local.JobDao
import com.emoulgen.jobtrackerapp.data.local.JobDatabase
import com.emoulgen.jobtrackerapp.data.repository.JobRepositoryImplementation
import com.emoulgen.jobtrackerapp.domain.repository.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = JobDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideDao(appDatabase: JobDatabase) = appDatabase.jobDao()

    @Provides
    @Singleton
    fun provideRepository(dao: JobDao): JobRepository = JobRepositoryImplementation(dao)
}