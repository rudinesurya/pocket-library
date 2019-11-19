package com.cookiesmile.pocket_library.screen.book_list;

import com.cookiesmile.pocket_library.di.ScreenScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface BookListComponent extends
    AndroidInjector<BookListController> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BookListController> {

    @Override
    public void seedInstance(BookListController instance) {
    }
  }
}
