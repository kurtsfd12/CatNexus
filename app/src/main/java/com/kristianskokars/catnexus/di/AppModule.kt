package com.kristianskokars.catnexus.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.kristianskokars.catnexus.core.BASE_URL
import com.kristianskokars.catnexus.core.CAT_DATABASE
import com.kristianskokars.catnexus.core.data.data_source.local.AndroidFileStorage
import com.kristianskokars.catnexus.core.data.data_source.local.CatDao
import com.kristianskokars.catnexus.core.data.data_source.local.CatDatabase
import com.kristianskokars.catnexus.core.data.data_source.remote.CatAPI
import com.kristianskokars.catnexus.core.data.data_source.remote.NetworkClient
import com.kristianskokars.catnexus.core.data.repository.OfflineFirstCatRepository
import com.kristianskokars.catnexus.core.domain.repository.CatRepository
import com.kristianskokars.catnexus.core.domain.repository.FileStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit = NetworkClient.retrofitClient(BASE_URL)

    @Provides
    @Singleton
    fun provideCatAPI(retrofit: Retrofit): CatAPI = retrofit.create(CatAPI::class.java)

    @Provides
    @Singleton
    fun provideCatDatabase(@ApplicationContext context: Context): CatDatabase =
        Room.databaseBuilder(
            context,
            CatDatabase::class.java,
            CAT_DATABASE,
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCatDao(db: CatDatabase): CatDao = db.catDao()

    @Provides
    @Singleton
    fun provideCatRepository(
        local: CatDao,
        remote: CatAPI,
        workManager: WorkManager,
    ): CatRepository = OfflineFirstCatRepository(local, remote, workManager)

    @Provides
    @Singleton
    fun provideFileStorage(@ApplicationContext context: Context): FileStorage = AndroidFileStorage(context)

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)
}
