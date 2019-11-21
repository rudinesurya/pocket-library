package com.cookiesmile.pocket_library.screen.settings;

import com.cookiesmile.pocket_library.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface SettingsComponent extends
    AndroidInjector<SettingsController> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<SettingsController> {

    @Override
    public void seedInstance(SettingsController instance) {
    }
  }
}
