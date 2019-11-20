package com.cookiesmile.pocket_library.screen.book_detail;


import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.data.model.BookDetail;
import com.cookiesmile.pocket_library.testutils.TestUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class BookDetailViewModelTest {

  private BookDetailViewModel viewModel;

  @Before
  public void setUp() throws Exception {
    viewModel = new BookDetailViewModel();
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
    BookDetail book = TestUtils
        .loadJson("mock/book.json", BookDetail.class);

    viewModel.resultUpdated().accept(book);

    viewModel.result().test().assertValue(book);
    viewModel.error().test().assertValue(-1);
  }

  @Test
  public void error() throws Exception {
    viewModel.onError().accept(new IOException());

    viewModel.error().test().assertValue(R.string.api_error_fetch_book);
  }
}
