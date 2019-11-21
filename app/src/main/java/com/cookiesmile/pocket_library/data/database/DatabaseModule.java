package com.cookiesmile.pocket_library.data.database;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

  @Provides
  @Singleton
  static StarredBookDatabase provideStarredBookDatabase(Context context) {
    return Room.databaseBuilder(context, StarredBookDatabase.class, "starred-book-database")
        .build();
  }

  @Provides
  @Singleton
  static StarredBookDao provideStarredBookDao(StarredBookDatabase database) {
    return database.starredBookDao();
  }
}
