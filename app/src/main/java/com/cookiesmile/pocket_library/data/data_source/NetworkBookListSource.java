package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.api.BookServerApiService;
import com.cookiesmile.pocket_library.data.model.BookListData;

import javax.inject.Inject;

import io.reactivex.Single;

public class NetworkBookListSource {

  private final BookServerApiService service;

  @Inject
  NetworkBookListSource(BookServerApiService service) {
    this.service = service;
  }

  public Single<BookListData> getData() {
    return Single.create(emitter -> {
      service.getBooks().doOnSuccess(list -> {
        emitter.onSuccess(BookListData.builder()
            .data(list)
            .source("network")
            .build());
      });
    });
  }
}
