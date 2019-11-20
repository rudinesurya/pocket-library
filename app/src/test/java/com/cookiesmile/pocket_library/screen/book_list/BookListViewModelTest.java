package com.cookiesmile.pocket_library.screen.book_list;


import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.testutils.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class BookListViewModelTest {

  private BookListViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    viewModel = new BookListViewModel();
  }

  @Test
  public void loading() throws Exception {
    viewModel.loadingUpdated().accept(true);

    viewModel.loading().test().assertValue(true);
  }

  @Test
  public void loading_false() throws Exception {
    viewModel.loadingUpdated().accept(false);

    viewModel.loading().test().assertValue(false);
  }

  @Test
  public void result() throws Exception {
    List<Book> books = TestUtils
        .loadJson("mock/books.json", List.class);
    viewModel.resultUpdated().accept(books);

    viewModel.result().test().assertValue(books);
    viewModel.error().test().assertValue(-1);
  }

  @Test
  public void error() throws Exception {
    viewModel.onError().accept(new IOException());

    viewModel.error().test().assertValue(R.string.api_error_fetch_books);
  }
}
