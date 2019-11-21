package com.cookiesmile.pocket_library.screen.starred;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.data.database.StarredBook;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.di.ScreenScope;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;
import com.cookiesmile.pocket_library.screen.utils.MyListAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        .map(starred -> {
          Set<Long> starredIds = new HashSet<>();
          for (StarredBook sb : starred) {
            starredIds.add(sb.getId());
          }
          return starredIds;
        })
        .subscribe(starredIds -> {
          repository.getBookList()
              .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
              .subscribe(books -> {
                List<Book> filtered = books.stream().filter(book -> starredIds.contains(book.id()))
                    .collect(Collectors.toList());
                viewModel.resultUpdated().accept(filtered);
              }, viewModel.onError());
        }, viewModel.onError());
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
