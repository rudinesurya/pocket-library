package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.BookDetail;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BookDetailDataSource {

  NetworkBookDetailSource networkBookDetailSource;

  @Inject
  BookDetailDataSource(
      NetworkBookDetailSource networkBookDetailSource) {
    this.networkBookDetailSource = networkBookDetailSource;
  }

  public Single<BookDetail> getDataFromNetwork(long id) {
    return networkBookDetailSource.getData(id);
  }
}
