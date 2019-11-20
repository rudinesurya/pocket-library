package com.cookiesmile.pocket_library.data;

import com.cookiesmile.pocket_library.data.api.BookServerApiService;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.data.model.BookDetail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;

@Singleton
public class MyRepository {

  private final BookServerApiService service;
  private final Scheduler scheduler;

  @Inject
  MyRepository(BookServerApiService service,
      @Named("network_scheduler") Scheduler scheduler) {
    this.service = service;
    this.scheduler = scheduler;
  }

  public Single<List<Book>> getBookList() {
    return service.getBooks().subscribeOn(scheduler);
  }

  public Single<BookDetail> getBookDetail(long id) {
    return service.getBookDetail(id).subscribeOn(scheduler);
  }

  public Single<List<Book>> getStarredBookList() {
    return Single.create(emitter -> {
      List<Book> dummy = new ArrayList<>();
      dummy.add(Book.builder()
          .id(100)
          .title("My Fav Book")
          .author("John")
          .isbn("123123")
          .price(123)
          .currencyCode("EUR")
          .build());

      emitter.onSuccess(dummy);
    });
  }
}
