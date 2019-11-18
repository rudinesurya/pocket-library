package com.cookiesmile.pocket_library.base;

import android.app.Activity;

import com.cookiesmile.pocket_library.di.ActivityKey;
import com.cookiesmile.pocket_library.main.MainActivity;
import com.cookiesmile.pocket_library.main.MainActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
    MainActivityComponent.class,
})
abstract class ActivityBindingModule {

  @Binds
  @IntoMap
  @ActivityKey(MainActivity.class)
  abstract AndroidInjector.Factory<? extends Activity> provideMainActivityInjector(
      MainActivityComponent.Builder builder);
}
