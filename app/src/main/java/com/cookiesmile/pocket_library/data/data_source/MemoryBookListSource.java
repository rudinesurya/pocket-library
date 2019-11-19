package com.cookiesmile.pocket_library.data.data_source;

import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.data.model.BookListData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MemoryBookListSource {

  private BookListData data;

  @Inject
  public MemoryBookListSource() {
    List<Book> dummy = new ArrayList<>();

    for (int i = 0; i < 10; ++i) {
      Book book = Book.builder()
          .id(i)
          .title("title#" + i)
          .isbn("abcd")
          .author("author#" + i)
          .description("description#" + i)
          .price(i * 100)
          .build();

      dummy.add(book);
    }

    this.data = BookListData.builder()
        .data(dummy)
        .source("memory")
        .build();
  }

  public Single<BookListData> getData() {
    return Single.create(emitter -> {
      if (data != null) {
        emitter.onSuccess(data);
      }
    });
  }

  public void cacheInMemory(List<Book> data) {
    this.data = BookListData.builder()
        .data(data)
        .source("memory")
        .build();
  }
}
