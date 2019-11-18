package com.cookiesmile.pocket_library.screen.main;

import com.cookiesmile.pocket_library.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface MainControllerComponent extends
    AndroidInjector<MainController> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<MainController> {

    @Override
    public void seedInstance(MainController instance) {
    }
  }
}