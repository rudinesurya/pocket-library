package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.BookListData;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class BookListDataSource {

  MemoryBookListSource memoryBookListSource;

  @Inject
  BookListDataSource(MemoryBookListSource memoryBookListSource) {
    this.memoryBookListSource = memoryBookListSource;
  }

  public Single<BookListData> getDataFromMemory() {
    return memoryBookListSource.getData();
  }
}
