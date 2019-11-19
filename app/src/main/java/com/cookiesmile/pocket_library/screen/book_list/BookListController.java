package com.cookiesmile.pocket_library.screen.book_list;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseController;
import com.cookiesmile.pocket_library.data.model.BookListData;
import com.cookiesmile.pocket_library.screen.book_list.utils.MyListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BookListController extends BaseController {

  @Inject
  BookListViewModel viewModel;

  @Inject
  BookListPresenter presenter;

  @BindView(R.id.loading_indicator)
  View loadingView;
  @BindView(R.id.tv_error)
  TextView errorText;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  @Override
  protected int layoutRes() {
    return R.layout.screen_book_list;
  }

  @Override
  protected void onViewBound(View view) {
    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerView.setAdapter(new MyListAdapter(presenter));
  }

  @Override
  protected Disposable[] subscriptions() {
    return new Disposable[]{
        viewModel.loading()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(loading -> {
          loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
          recyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
          errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
        }),

        viewModel.result()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::PopulateRecyclerView),

        viewModel.error()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(errorRes -> {
          if (errorRes == -1) {
            errorText.setText(null);
            errorText.setVisibility(View.GONE);
          } else {
            errorText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            errorText.setText(errorRes);
          }
        })
    };
  }

  private void PopulateRecyclerView(BookListData data) {
    ((MyListAdapter) recyclerView.getAdapter()).setData(data.data());
    Toast.makeText(getApplicationContext(), "fetched from: " + data.source(), Toast.LENGTH_LONG)
        .show();
  }
}
