package com.cookiesmile.pocket_library.screen.book_list;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.di.ScreenScope;
import com.cookiesmile.pocket_library.screen.book_list.utils.MyListAdapter;

import javax.inject.Inject;

@ScreenScope
public class BookListPresenter implements MyListAdapter.ItemClickListener {

  @Inject
  BookListPresenter(BookListViewModel viewModel, MyRepository repository) {
    repository.getBookList()
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }

  @Override
  public void onItemClickListener(Book book) {

  }
}
