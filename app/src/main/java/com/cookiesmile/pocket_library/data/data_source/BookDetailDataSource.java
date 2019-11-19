package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.BookDetailData;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BookDetailDataSource {

  MemoryBookDetailSource memoryBookDetailSource;

  @Inject
  BookDetailDataSource(MemoryBookDetailSource memoryBookDetailSource) {
    this.memoryBookDetailSource = memoryBookDetailSource;
  }

  public Single<BookDetailData> getDataFromMemory() {
    return memoryBookDetailSource.getData();
  }
}
