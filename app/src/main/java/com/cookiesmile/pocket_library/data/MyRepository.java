package com.cookiesmile.pocket_library.data;

import com.cookiesmile.pocket_library.data.data_source.BookDetailDataSource;
import com.cookiesmile.pocket_library.data.data_source.BookListDataSource;
import com.cookiesmile.pocket_library.data.model.BookDetailData;
import com.cookiesmile.pocket_library.data.model.BookListData;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;

@Singleton
public class MyRepository {

  private final BookListDataSource bookListDataSource;
  private final BookDetailDataSource bookDetailDataSource;
  private final Scheduler scheduler;

  @Inject
  MyRepository(BookListDataSource bookListDataSource, BookDetailDataSource bookDetailDataSource,
      @Named("network_scheduler") Scheduler scheduler) {
    this.bookListDataSource = bookListDataSource;
    this.bookDetailDataSource = bookDetailDataSource;
    this.scheduler = scheduler;
  }

  public Single<BookListData> getBookList() {
    return bookListDataSource.getDataFromMemory().subscribeOn(scheduler);
  }

  public Single<BookDetailData> getBookDetail(long id) {
    return bookDetailDataSource.getDataFromMemory().subscribeOn(scheduler);
  }
}
