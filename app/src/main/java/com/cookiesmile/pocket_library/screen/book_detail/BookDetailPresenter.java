package com.cookiesmile.pocket_library.screen.book_detail;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.di.ScreenScope;

import javax.inject.Inject;

@ScreenScope
public class BookDetailPresenter {

  @Inject
  BookDetailPresenter(BookDetailViewModel viewModel, MyRepository repository) {
    repository.getBookDetail(1)
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }
}
