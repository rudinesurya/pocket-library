package com.cookiesmile.pocket_library.data;

import com.cookiesmile.pocket_library.data.data_source.BookListDataSource;
import com.cookiesmile.pocket_library.data.model.BookListData;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;

@Singleton
public class MyRepository {

  private final BookListDataSource dataSource;
  private final Scheduler scheduler;

  @Inject
  MyRepository(BookListDataSource dataSource, @Named("network_scheduler") Scheduler scheduler) {
    this.dataSource = dataSource;
    this.scheduler = scheduler;
  }

  public Single<BookListData> getBookList() {
    return dataSource.getDataFromMemory().subscribeOn(scheduler);
  }
}
