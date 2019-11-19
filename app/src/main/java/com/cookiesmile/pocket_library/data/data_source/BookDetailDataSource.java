package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.BookDetailData;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BookDetailDataSource {

  MemoryBookDetailSource memoryBookDetailSource;
  NetworkBookDetailSource networkBookDetailSource;

  @Inject
  BookDetailDataSource(MemoryBookDetailSource memoryBookDetailSource,
      NetworkBookDetailSource networkBookDetailSource) {
    this.memoryBookDetailSource = memoryBookDetailSource;
    this.networkBookDetailSource = networkBookDetailSource;
  }

  public Single<BookDetailData> getDataFromMemory(long id) {
    return memoryBookDetailSource.getData(id);
  }

  public Single<BookDetailData> getDataFromNetwork(long id) {
    return networkBookDetailSource.getData(id);
  }

}
