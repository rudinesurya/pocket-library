package com.cookiesmile.pocket_library.screen.book_detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;

import com.bluelinelabs.conductor.Controller;
import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseController;
import com.cookiesmile.pocket_library.data.model.BookDetail;
import com.cookiesmile.pocket_library.data.utils.MyCurrencyStringBuilder;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BookDetailController extends BaseController implements OnMenuItemClickListener {

  static final String BOOK_ID_KEY = "book_id";

  @Inject
  ScreenNavigation screenNavigation;

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

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.loading_indicator)
  View loadingView;
  @BindView(R.id.tv_error)
  TextView errorText;

  @BindView(R.id.layout)
  ViewGroup layout;
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
  protected void onViewBound(View view) {
    toolbar.setNavigationIcon(R.drawable.ic_back);
    toolbar.setNavigationOnClickListener(v -> screenNavigation.pop());

    toolbar.inflateMenu(R.menu.popup_menu);
    toolbar.setOnMenuItemClickListener(this);
  }

  @Override
  protected Disposable[] subscriptions() {
    return new Disposable[]{
        viewModel.loading()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loading -> {
          loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
          layout.setVisibility(loading ? View.GONE : View.VISIBLE);
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
            layout.setVisibility(View.GONE);
            errorText.setText(errorRes);
          }
        })
    };
  }

  private void FillBookDetail(BookDetail data) {
    BookDetail bookDetail = data;

    titleText.setText(bookDetail.title());
    authorText.setText(bookDetail.author());
    isbnText.setText(bookDetail.isbn());
    descriptionText.setText(bookDetail.description());
    priceText
        .setText(MyCurrencyStringBuilder.create(bookDetail.price(), bookDetail.currencyCode()));
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.star_book:
        return true;

      case R.id.not_interested:
        return true;
    }
    return false;
  }
}
