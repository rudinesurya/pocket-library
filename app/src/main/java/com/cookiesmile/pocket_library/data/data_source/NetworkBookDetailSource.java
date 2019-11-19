package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.api.BookServerApiService;
import com.cookiesmile.pocket_library.data.model.BookDetailData;

import javax.inject.Inject;

import io.reactivex.Single;

public class NetworkBookDetailSource {

  private final BookServerApiService service;

  @Inject
  NetworkBookDetailSource(BookServerApiService service) {
    this.service = service;
  }

  public Single<BookDetailData> getData(long id) {
    return Single.create(emitter -> {
      service.getBookDetail(id).doOnSuccess(book -> {
        emitter.onSuccess(BookDetailData.builder()
            .data(book)
            .source("network")
            .build());
      });
    });
  }
}
