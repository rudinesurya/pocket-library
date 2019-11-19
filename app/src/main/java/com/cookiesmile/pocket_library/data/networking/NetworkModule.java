package com.cookiesmile.pocket_library.data.networking;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;

@Module
abstract class NetworkModule {

  @Provides
  @Singleton
  static Call.Factory provideOkHttp() {
    return new OkHttpClient.Builder().build();
  }

  @Provides
  @Named("base_url")
  static String provideBaseUrl() {
    return "http://tpbookserver.herokuapp.com/";
  }

  @Provides
  @Named("network_scheduler")
  static Scheduler provideNetworkScheduler() {
    return Schedulers.io();
  }
}