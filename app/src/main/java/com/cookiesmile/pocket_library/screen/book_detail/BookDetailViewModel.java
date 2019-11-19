package com.cookiesmile.pocket_library.screen.book_detail;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.data.model.BookDetailData;
import com.cookiesmile.pocket_library.di.ScreenScope;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
public class BookDetailViewModel {

  private final BehaviorRelay<BookDetailData> resultRelay = BehaviorRelay.create();
  private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
  private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();

  @Inject
  BookDetailViewModel() {

  }

  Observable<Boolean> loading() {
    return loadingRelay;
  }

  Observable<BookDetailData> result() {
    return resultRelay;
  }

  Observable<Integer> error() {
    return errorRelay;
  }

  Consumer<Boolean> loadingUpdated() {
    return loadingRelay;
  }

  Consumer<BookDetailData> resultUpdated() {
    errorRelay.accept(-1);
    return resultRelay;
  }

  Consumer<Throwable> onError() {
    return throwable -> {
      Timber.e(throwable, "Error fetching book data");
      errorRelay.accept(R.string.api_error_fetch_book);
    };
  }
}