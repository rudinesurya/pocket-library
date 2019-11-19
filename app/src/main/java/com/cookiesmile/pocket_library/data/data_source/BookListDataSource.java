package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.Book;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BookListDataSource {

  NetworkBookListSource networkBookListSource;

  @Inject
  BookListDataSource(NetworkBookListSource networkBookListSource) {
    this.networkBookListSource = networkBookListSource;
  }

  public Single<List<Book>> getDataFromNetwork() {
    return networkBookListSource.getData();
  }
}
