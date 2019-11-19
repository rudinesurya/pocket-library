package com.cookiesmile.pocket_library.data.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class BookServerApiServiceModule {

  @Provides
  @Singleton
  static BookServerApiService provideBookServerApiService(Retrofit retrofit) {
    return retrofit.create(BookServerApiService.class);
  }
}
