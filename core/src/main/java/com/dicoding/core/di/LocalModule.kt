package com.dicoding.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.core.data.local.room.Database
import com.dicoding.core.data.local.room.LocalDataSourceImpl
import com.dicoding.core.data.local.room.dao.MealDao
import com.dicoding.core.data.local.room.dao.MealDetailDao
import com.dicoding.core.data.local.room.dao.MealFavoriteDao
import com.dicoding.core.domain.contract.source.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideStoryDatabase(@ApplicationContext context: Context): Database {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "database"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMealDao(database: Database): MealDao = database.mealDao()

    @Provides
    fun provideMealFavoriteDao(database: Database): MealFavoriteDao = database.mealFavoriteDao()

    @Provides
    fun provideMealDetailDao(database: Database) = database.mealDetailDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(mealDao: MealDao, mealFavoriteDao: MealFavoriteDao, mealDetailDao: MealDetailDao): LocalDataSource{
        return LocalDataSourceImpl(mealDao, mealFavoriteDao, mealDetailDao)
    }

}