package com.cookiesmile.pocket_library.screen.book_list;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.di.ScreenScope;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;
import com.cookiesmile.pocket_library.screen.book_list.utils.MyListAdapter;

import javax.inject.Inject;

@ScreenScope
public class BookListPresenter implements MyListAdapter.ItemClickListener {

  private final ScreenNavigation screenNavigation;

  @Inject
  BookListPresenter(BookListViewModel viewModel, MyRepository repository,
      ScreenNavigation screenNavigation) {
    this.screenNavigation = screenNavigation;

    repository.getBookList()
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }

  @Override
  public void onItemClickListener(Book book) {
    screenNavigation.goToBookDetail(book.id());
  }
}
