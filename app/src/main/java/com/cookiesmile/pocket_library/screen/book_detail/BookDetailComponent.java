package com.cookiesmile.pocket_library.screen.book_detail;

import com.cookiesmile.pocket_library.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface BookDetailComponent extends
    AndroidInjector<BookDetailController> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BookDetailController> {

    @Override
    public void seedInstance(BookDetailController instance) {
    }
  }
}
