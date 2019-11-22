package com.cookiesmile.pocket_library.data.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import timber.log.Timber;

@Module
abstract class NetworkModule {

  static final String HEADER_CACHE_CONTROL = "Cache-Control";
  static final String HEADER_PRAGMA = "Pragma";

  @Provides
  @Singleton
  static ConnectivityManager provideConnectivityManager(Context context) {
    return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Provides
  @Singleton
  @Nullable
  static NetworkInfo provideNetworkInfo(ConnectivityManager connectivityManager) {
    return connectivityManager.getActiveNetworkInfo();
  }

  @Provides
  @Singleton
  static Call.Factory provideOkHttp(Cache cache,
      HttpLoggingInterceptor httpLoggingInterceptor,
      @Named("offline_interceptor") Interceptor offlineInterceptor,
      @Named("network_interceptor") Interceptor networkInterceptor) {
    return new OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(networkInterceptor) // only intercepts when network is available
        .addInterceptor(offlineInterceptor)
        .build();
  }

  @Provides
  @Singleton
  static Cache provideCache(Context context, @Named("cache_size") long cacheSize) {
    return new Cache(new File(context.getCacheDir(), "retrofit_cache"), cacheSize);
  }

  @Provides
  @Singleton
  static HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor httpLoggingInterceptor =
        new HttpLoggingInterceptor(message -> Timber.d("http log: " + message));
    httpLoggingInterceptor.level(Level.BASIC);
    return httpLoggingInterceptor;
  }

  @Provides
  @Named("offline_interceptor")
  @Singleton
  static Interceptor provideOfflineInterceptor(@Nullable NetworkInfo networkInfo) {
    return chain -> {
      Timber.d("offline interceptor: called.");

      String cacheHeaderValue = networkInfo != null && networkInfo.isConnected()
          ? "public, max-age=600"
          : "public, only-if-cached, max-stale=600";

      Request request = chain.request();
      Response response = chain.proceed(request);

      return response.newBuilder()
          .removeHeader(HEADER_PRAGMA)
          .removeHeader(HEADER_CACHE_CONTROL)
          .header(HEADER_CACHE_CONTROL, cacheHeaderValue)
          .build();
    };
  }

  @Provides
  @Named("network_interceptor")
  @Singleton
  static Interceptor provideNetworkInterceptor(@Nullable NetworkInfo networkInfo) {
    return chain -> {
      Timber.d("network interceptor: called.");

      String cacheHeaderValue = networkInfo != null && networkInfo.isConnected()
          ? "public, max-age=600"
          : "public, only-if-cached, max-stale=600";

      Request originalRequest = chain.request();
      Request request = originalRequest.newBuilder().build();
      Response response = chain.proceed(request);

      return response.newBuilder()
          .removeHeader(HEADER_PRAGMA)
          .removeHeader(HEADER_CACHE_CONTROL)
          .header(HEADER_CACHE_CONTROL, cacheHeaderValue)
          .build();
    };
  }

  @Provides
  @Named("cache_size")
  static long provideCacheSize() {
    return 20 * 1024 * 1024; // 20MB
  }

  @Provides
  @Named("base_url")
  static String provideBaseUrl() {
    return "https://tpbookserver.herokuapp.com/";
  }

  @Provides
  @Named("network_scheduler")
  static Scheduler provideNetworkScheduler() {
    return Schedulers.io();
  }
}