package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.api.BookServerApiService;
import com.cookiesmile.pocket_library.data.model.Book;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class NetworkBookListSource {

  private final BookServerApiService service;

  @Inject
  NetworkBookListSource(BookServerApiService service) {
    this.service = service;
  }

  public Single<List<Book>> getData() {
    return service.getBooks();
  }
}
