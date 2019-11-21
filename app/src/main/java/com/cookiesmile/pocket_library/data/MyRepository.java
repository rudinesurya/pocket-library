package com.cookiesmile.pocket_library.data;

import com.cookiesmile.pocket_library.data.api.BookServerApiService;
import com.cookiesmile.pocket_library.data.database.StarredBook;
import com.cookiesmile.pocket_library.data.database.StarredBookDao;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.data.model.BookDetail;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import timber.log.Timber;

@Singleton
public class MyRepository {

  private final BookServerApiService service;
  private final StarredBookDao starredBookDao;
  private final Scheduler scheduler;

  @Inject
  MyRepository(BookServerApiService service, StarredBookDao starredBookDao,
      @Named("network_scheduler") Scheduler scheduler) {
    this.service = service;
    this.starredBookDao = starredBookDao;
    this.scheduler = scheduler;
  }

  public Single<List<Book>> getBookList() {
    return service.getBooks().subscribeOn(scheduler);
  }

  public Single<BookDetail> getBookDetail(long id) {
    return service.getBookDetail(id).subscribeOn(scheduler);
  }

  public Flowable<List<StarredBook>> getStarredBookList() {
    return starredBookDao.getStarredBooks().subscribeOn(scheduler);
  }

  public void addStarredBook(long id) {
    runDbAction(() -> {
      starredBookDao.addStarred(new StarredBook(id));
    });
  }

  public void addStarredBook(Book book) {
    runDbAction(() -> {
      starredBookDao.addStarred(new StarredBook(book.id()));
    });
  }

  public void deleteStarredBook(long id) {
    runDbAction(() -> {
      starredBookDao.deleteStarred(new StarredBook(id));
    });
  }

  public void deleteStarredBook(Book book) {
    runDbAction(() -> {
      starredBookDao.deleteStarred(new StarredBook(book.id()));
    });
  }

  private void runDbAction(Action action) {
    Completable.fromAction(action)
        .subscribeOn(scheduler)
        .subscribe(() -> {
        }, throwable -> {
          Timber.e(throwable, "Error performing database action");
        });
  }
}
