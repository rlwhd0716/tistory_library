package com.example.cleanarchitecturehilt.di

import android.content.Context
import androidx.room.Room
import com.example.cleanarchitecturehilt.data.database.MyDatabase
import com.example.cleanarchitecturehilt.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase =
        Room.databaseBuilder(context, MyDatabase::class.java, "my_db.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    fun provideUserDao(db: MyDatabase): UserDao = db.userDao()
}

