package com.cookiesmile.pocket_library.screen.starred;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.di.ScreenScope;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
public class StarredBookListViewModel {

  private final BehaviorRelay<List<Book>> resultRelay = BehaviorRelay.create();
  private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
  private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();

  @Inject
  StarredBookListViewModel() {

  }

  Observable<Boolean> loading() {
    return loadingRelay;
  }

  Observable<List<Book>> result() {
    return resultRelay;
  }

  Observable<Integer> error() {
    return errorRelay;
  }

  Consumer<Boolean> loadingUpdated() {
    return loadingRelay;
  }

  Consumer<List<Book>> resultUpdated() {
    errorRelay.accept(-1);
    return resultRelay;
  }

  Consumer<Throwable> onError() {
    return throwable -> {
      Timber.e(throwable, "Error fetching books data");
      errorRelay.accept(R.string.error_fetch_books);
    };
  }
}
