package com.cookiesmile.pocket_library.screen.book_detail;


import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.data.model.BookDetail;
import com.cookiesmile.pocket_library.testutils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class BookDetailPresenterTest {

  private final int BOOK_ID = 100;
  @Mock
  MyRepository repository;
  @Mock
  BookDetailViewModel viewModel;
  @Mock
  Consumer<Throwable> onErrorConsumer;
  @Mock
  Consumer<BookDetail> onSuccessConsumer;
  @Mock
  Consumer<Boolean> loadingConsumer;
  private BookDetailPresenter presenter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(viewModel.loadingUpdated()).thenReturn(loadingConsumer);
    when(viewModel.onError()).thenReturn(onErrorConsumer);
    when(viewModel.resultUpdated()).thenReturn(onSuccessConsumer);
  }

  @Test
  public void bookLoaded() throws Exception {
    BookDetail result = setUpSuccess();
    initializePresenter();

    verify(repository).getBookDetail(BOOK_ID);
    verify(onSuccessConsumer).accept(result);
    verifyZeroInteractions(onErrorConsumer);
  }

  @Test
  public void bookLoadedError() throws Exception {
    Throwable error = setUpError();
    initializePresenter();

    verify(onErrorConsumer).accept(error);
    verifyZeroInteractions(onSuccessConsumer);
  }

  @Test
  public void loadingSuccess() throws Exception {
    setUpSuccess();
    initializePresenter();

    InOrder inOrder = Mockito.inOrder(loadingConsumer);
    inOrder.verify(loadingConsumer).accept(true);
    inOrder.verify(loadingConsumer).accept(false);
  }

  @Test
  public void loadingError() throws Exception {
    //noinspection ThrowableNotThrown
    setUpError();
    initializePresenter();

    InOrder inOrder = Mockito.inOrder(loadingConsumer);
    inOrder.verify(loadingConsumer).accept(true);
    inOrder.verify(loadingConsumer).accept(false);
  }

  //  Helper methods
  private void initializePresenter() {
    presenter = new BookDetailPresenter(BOOK_ID, viewModel, repository);
  }

  private BookDetail setUpSuccess() {
    BookDetail book = TestUtils
        .loadJson("mock/book.json", BookDetail.class);

    when(repository.getBookDetail(BOOK_ID)).thenReturn(Single.just(book));

    return book;
  }

  private Throwable setUpError() {
    Throwable error = new IOException();
    when(repository.getBookDetail(BOOK_ID)).thenReturn(Single.error(error));

    return error;
  }
}
