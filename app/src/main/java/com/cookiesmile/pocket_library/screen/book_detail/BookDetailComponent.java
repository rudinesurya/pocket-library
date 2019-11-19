package com.cookiesmile.pocket_library.screen.book_detail;

import com.cookiesmile.pocket_library.di.ScreenScope;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface BookDetailComponent extends
    AndroidInjector<BookDetailController> {

  @Subcomponent.Builder
  abstract class Builder extends AndroidInjector.Builder<BookDetailController> {

    @BindsInstance
    public abstract void bindBookId(@Named("book_id") long id);
    @Override
    public void seedInstance(BookDetailController instance) {
      bindBookId(instance.getArgs().getLong(BookDetailController.BOOK_ID_KEY));
    }
  }
}
