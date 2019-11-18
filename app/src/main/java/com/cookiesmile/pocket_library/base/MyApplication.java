package com.cookiesmile.pocket_library.base;

import android.app.Application;

import com.cookiesmile.pocket_library.BuildConfig;
import com.cookiesmile.pocket_library.di.ActivityInjector;

import javax.inject.Inject;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class MyApplication extends Application {

  protected ApplicationComponent component;
  @Inject
  ActivityInjector activityInjector;

  @Override
  public void onCreate() {
    super.onCreate();

    component = initComponent();
    component.inject(this);

    if (BuildConfig.DEBUG) {
      Timber.plant(new DebugTree());
    }
  }

  protected ApplicationComponent initComponent() {
    return DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ActivityInjector getActivityInjector() {
    return activityInjector;
  }
}
