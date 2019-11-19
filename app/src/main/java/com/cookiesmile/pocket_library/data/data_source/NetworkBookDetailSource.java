package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.api.BookServerApiService;
import com.cookiesmile.pocket_library.data.model.BookDetail;

import javax.inject.Inject;

import io.reactivex.Single;

public class NetworkBookDetailSource {

  private final BookServerApiService service;

  @Inject
  NetworkBookDetailSource(BookServerApiService service) {
    this.service = service;
  }

  public Single<BookDetail> getData(long id) {
    return service.getBookDetail(id);
  }
}
