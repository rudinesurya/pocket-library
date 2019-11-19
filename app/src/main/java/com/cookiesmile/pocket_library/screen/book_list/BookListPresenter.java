package com.cookiesmile.pocket_library.screen.book_list;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.di.ScreenScope;

import javax.inject.Inject;

@ScreenScope
public class BookListPresenter {

  @Inject
  BookListPresenter(BookListViewModel viewModel, MyRepository repository) {
    repository.getBookList()
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }
}
