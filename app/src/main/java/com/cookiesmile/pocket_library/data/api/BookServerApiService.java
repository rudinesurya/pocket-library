package com.cookiesmile.pocket_library.data.api;

import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.data.model.BookDetail;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookServerApiService {

  @GET("books")
  Single<List<Book>> getBooks();

  @GET("book/{id}")
  Single<BookDetail> getBookDetail(@Path("id") long id);
}
