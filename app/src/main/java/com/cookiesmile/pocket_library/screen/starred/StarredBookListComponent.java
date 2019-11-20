package com.cookiesmile.pocket_library.screen.starred;

import com.cookiesmile.pocket_library.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface StarredBookListComponent extends
    AndroidInjector<StarredBookListController> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<StarredBookListController> {

    @Override
    public void seedInstance(StarredBookListController instance) {
    }
  }
}
