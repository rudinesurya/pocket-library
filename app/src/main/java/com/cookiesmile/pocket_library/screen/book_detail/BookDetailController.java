package com.cookiesmile.pocket_library.screen.book_detail;

import android.view.View;
import android.widget.TextView;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseController;
import com.cookiesmile.pocket_library.data.model.BookDetailData;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class BookDetailController extends BaseController {

  @Inject
  BookDetailViewModel viewModel;

  @Inject
  BookDetailPresenter presenter;

  @BindView(R.id.loading_indicator)
  View loadingView;
  @BindView(R.id.tv_error)
  TextView errorText;

  @Override
  protected int layoutRes() {
    return R.layout.screen_book_list;
  }

  @Override
  protected Disposable[] subscriptions() {
    return new Disposable[]{
        viewModel.loading()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loading -> {
          loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);

          errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
        }),

        viewModel.result()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::Foo),

        viewModel.error()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(errorRes -> {
          if (errorRes == -1) {
            errorText.setText(null);
            errorText.setVisibility(View.GONE);
          } else {
            errorText.setVisibility(View.VISIBLE);

            errorText.setText(errorRes);
          }
        })
    };
  }

  private void Foo(BookDetailData data) {
    Timber.d(data.toString());
  }
}
