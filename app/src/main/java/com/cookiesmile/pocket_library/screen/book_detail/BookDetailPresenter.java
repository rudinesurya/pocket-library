package com.cookiesmile.pocket_library.screen.book_detail;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.di.ScreenScope;

import javax.inject.Inject;
import javax.inject.Named;

@ScreenScope
public class BookDetailPresenter {

  @Inject
  BookDetailPresenter(@Named("book_id") long id, BookDetailViewModel viewModel,
      MyRepository repository) {
    repository.getBookDetail(id)
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }
}
