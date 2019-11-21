package com.cookiesmile.pocket_library.screen.book_detail;

import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.di.ScreenScope;

import javax.inject.Inject;
import javax.inject.Named;

@ScreenScope
public class BookDetailPresenter implements OnMenuItemClickListener {

  private final MyRepository repository;
  private final long bookId;

  @Inject
  BookDetailPresenter(@Named("book_id") long id, BookDetailViewModel viewModel,
      MyRepository repository) {
    this.repository = repository;
    this.bookId = id;

    repository.getBookDetail(id)
        .doOnSubscribe(__ -> viewModel.loadingUpdated().accept(true))
        .doOnEvent((d, t) -> viewModel.loadingUpdated().accept(false))
        .subscribe(viewModel.resultUpdated(), viewModel.onError());
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.star_book:
        repository.addStarredBook(bookId);
        return true;

      case R.id.not_interested:
        repository.deleteStarredBook(bookId);
        return true;
    }
    return false;
  }
}
