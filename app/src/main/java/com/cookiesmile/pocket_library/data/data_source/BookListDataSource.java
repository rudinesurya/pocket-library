package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.BookListData;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BookListDataSource {

  MemoryBookListSource memoryBookListSource;
  NetworkBookListSource networkBookListSource;

  @Inject
  BookListDataSource(MemoryBookListSource memoryBookListSource,
      NetworkBookListSource networkBookListSource) {
    this.memoryBookListSource = memoryBookListSource;
    this.networkBookListSource = networkBookListSource;
  }

  public Single<BookListData> getDataFromMemory() {
    return memoryBookListSource.getData();
  }

  public Single<BookListData> getDataFromNetwork() {
    return networkBookListSource.getData();
  }
}
