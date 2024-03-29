package com.cookiesmile.pocket_library.screen.starred;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseController;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;
import com.cookiesmile.pocket_library.screen.utils.MyListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class StarredBookListController extends BaseController {

  @Inject
  StarredBookListViewModel viewModel;
  @Inject
  StarredBookListPresenter presenter;
  @Inject
  ScreenNavigation screenNavigation;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.loading_indicator)
  View loadingView;
  @BindView(R.id.tv_error)
  TextView errorText;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.tv_empty_splash)
  ViewGroup emptySplash;

  @Override
  protected int layoutRes() {
    return R.layout.screen_starred_book_list;
  }

  @Override
  protected void onViewBound(View view) {
    toolbar.setTitle("All starred books collection");
    toolbar.setNavigationIcon(R.drawable.ic_back);
    toolbar.setNavigationOnClickListener(v -> screenNavigation.pop());

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
          recyclerView.setVisibility(View.GONE);
          emptySplash.setVisibility(View.GONE);
          errorText.setVisibility(loading ? View.GONE : errorText.getVisibility());
        }),

        viewModel.result()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(data -> {
          if (data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptySplash.setVisibility(View.VISIBLE);
          } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptySplash.setVisibility(View.GONE);
            PopulateRecyclerView(data);
          }
        }),

        viewModel.error()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(errorRes -> {
          if (errorRes == -1) {
            errorText.setText(null);
            errorText.setVisibility(View.GONE);
          } else {
            errorText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptySplash.setVisibility(View.GONE);
            errorText.setText(errorRes);
          }
        })
    };
  }

  private void PopulateRecyclerView(List<Book> data) {
    ((MyListAdapter) recyclerView.getAdapter()).setData(data);
  }
}
