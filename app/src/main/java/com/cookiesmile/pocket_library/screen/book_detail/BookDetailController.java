package com.cookiesmile.pocket_library.screen.book_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseController;
import com.cookiesmile.pocket_library.data.model.BookDetail;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BookDetailController extends BaseController {

  static final String BOOK_ID_KEY = "book_id";

  public BookDetailController(Bundle bundle) {
    super(bundle);
  }

  public static Controller newInstance(long id) {
    Bundle bundle = new Bundle();
    bundle.putLong(BOOK_ID_KEY, id);
    return new BookDetailController(bundle);
  }

  @Inject
  BookDetailViewModel viewModel;

  @Inject
  BookDetailPresenter presenter;

  @BindView(R.id.loading_indicator)
  View loadingView;
  @BindView(R.id.tv_error)
  TextView errorText;

  @BindView(R.id.tv_title)
  TextView titleText;
  @BindView(R.id.tv_author)
  TextView authorText;
  @BindView(R.id.tv_isbn)
  TextView isbnText;
  @BindView(R.id.tv_description)
  TextView descriptionText;
  @BindView(R.id.tv_price)
  TextView priceText;

  @Override
  protected int layoutRes() {
    return R.layout.screen_book_detail;
  }

  @Override
  protected Disposable[] subscriptions() {
    return new Disposable[]{
        viewModel.loading()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loading -> {
          loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
          SetBookDetailVisibility(loading ? View.GONE : View.VISIBLE);
          errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
        }),

        viewModel.result()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::FillBookDetail),

        viewModel.error()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(errorRes -> {
          if (errorRes == -1) {
            errorText.setText(null);
            errorText.setVisibility(View.GONE);
          } else {
            errorText.setVisibility(View.VISIBLE);
            SetBookDetailVisibility(View.GONE);
            errorText.setText(errorRes);
          }
        })
    };
  }

  private void SetBookDetailVisibility(int visibility) {
    titleText.setVisibility(visibility);
    authorText.setVisibility(visibility);
    isbnText.setVisibility(visibility);
    descriptionText.setVisibility(visibility);
    priceText.setVisibility(visibility);
  }

  private void FillBookDetail(BookDetail data) {
    BookDetail bookDetail = data;
    titleText.setText(bookDetail.title());
    authorText.setText(bookDetail.author());
    isbnText.setText(bookDetail.isbn());
    descriptionText.setText(bookDetail.description());
    priceText.setText(String.valueOf(bookDetail.price()));
  }
}
