package com.cookiesmile.pocket_library.screen.book_list;

import com.cookiesmile.pocket_library.data.MyRepository;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;
import com.cookiesmile.pocket_library.testutils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class BookListPresenterTest {

  @Mock
  MyRepository repository;
  @Mock
  BookListViewModel viewModel;
  @Mock
  Consumer<Throwable> onErrorConsumer;
  @Mock
  Consumer<List<Book>> onSuccessConsumer;
  @Mock
  Consumer<Boolean> loadingConsumer;
  @Mock
  ScreenNavigation screenNavigation;

  private BookListPresenter presenter;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(viewModel.loadingUpdated()).thenReturn(loadingConsumer);
    when(viewModel.onError()).thenReturn(onErrorConsumer);
    when(viewModel.resultUpdated()).thenReturn(onSuccessConsumer);
  }

  @Test
  public void booksLoaded() throws Exception {
    List<Book> books = setUpSuccess();
    initializePresenter();

    verify(repository).getBookList();
    verify(onSuccessConsumer).accept(books);
    verifyZeroInteractions(onErrorConsumer);
  }

  @Test
  public void booksLoadedError() throws Exception {
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
    presenter = new BookListPresenter(viewModel, repository, screenNavigation);
  }

  private List<Book> setUpSuccess() {
    List<Book> books = TestUtils
        .loadJson("mock/books.json", List.class);

    when(repository.getBookList()).thenReturn(Single.just(books));

    return books;
  }

  private Throwable setUpError() {
    Throwable error = new IOException();
    when(repository.getBookList()).thenReturn(Single.error(error));

    return error;
  }
}
