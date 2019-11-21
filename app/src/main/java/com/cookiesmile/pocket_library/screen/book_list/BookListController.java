package com.cookiesmile.pocket_library.screen.book_list;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
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

public class BookListController extends BaseController implements OnMenuItemClickListener {

  @Inject
  BookListViewModel viewModel;
  @Inject
  BookListPresenter presenter;
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

  @Override
  protected int layoutRes() {
    return R.layout.screen_book_list;
  }

  @Override
  protected void onViewBound(View view) {
    toolbar.setTitle("All books collection");
    toolbar.inflateMenu(R.menu.main_menu);
    toolbar.setOnMenuItemClickListener(this);

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

  private void PopulateRecyclerView(List<Book> data) {
    recyclerView.setVisibility(View.VISIBLE);
    ((MyListAdapter) recyclerView.getAdapter()).setData(data);
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.starred:
        screenNavigation.goToStarredBookList();
        return true;

      case R.id.settings:
        screenNavigation.goToSettings();
        return true;
    }
    return false;
  }
}
