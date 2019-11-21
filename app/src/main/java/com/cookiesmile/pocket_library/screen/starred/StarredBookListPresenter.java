package com.cookiesmile.pocket_library.screen.starred;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.di.ScreenScope;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;
import com.cookiesmile.pocket_library.screen.book_list.utils.MyListAdapter;

import javax.inject.Inject;

@ScreenScope
public class StarredBookListPresenter implements MyListAdapter.ItemClickListener {

  private final MyRepository repository;
  private final ScreenNavigation screenNavigation;

  @Inject
  StarredBookListPresenter(StarredBookListViewModel viewModel, MyRepository repository,
      ScreenNavigation screenNavigation) {
    this.repository = repository;
    this.screenNavigation = screenNavigation;

    repository.getStarredBookList()
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEach(__ -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }

  @Override
  public void onItemClickListener(Book book) {
    screenNavigation.goToBookDetail(book.id());
  }

  @Override
  public void onStarItemClickListener(Book book) {
    repository.addStarredBook(book);
  }

  @Override
  public void onUnStarItemClickListener(Book book) {
    repository.deleteStarredBook(book);
  }
}
