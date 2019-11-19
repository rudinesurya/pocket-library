package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.BookDetail;
import com.cookiesmile.pocket_library.data.model.BookDetailData;

import javax.inject.Inject;

import io.reactivex.Single;

public class MemoryBookDetailSource {

  private BookDetailData data;

  @Inject
  public MemoryBookDetailSource() {
    BookDetail dummy = BookDetail.builder()
        .id(123)
        .title("title#123")
        .isbn("abcd")
        .author("author#123")
        .description("asdasdasd")
        .price(10000)
        .build();

    this.data = BookDetailData.builder()
        .data(dummy)
        .source("memory")
        .build();
  }

  public Single<BookDetailData> getData() {
    return Single.create(emitter -> {
      if (data != null) {
        emitter.onSuccess(data);
      }
    });
  }

  public void cacheInMemory(BookDetail data) {
    this.data = BookDetailData.builder()
        .data(data)
        .source("memory")
        .build();
  }
}
