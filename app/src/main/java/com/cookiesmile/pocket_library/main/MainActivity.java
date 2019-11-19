package com.cookiesmile.pocket_library.main;

import com.bluelinelabs.conductor.Controller;
import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseActivity;
import com.cookiesmile.pocket_library.screen.book_list.BookListController;

public class MainActivity extends BaseActivity {

  @Override
  protected int layoutRes() {
    return R.layout.activity_main;
  }

  @Override
  protected Controller initialScreen() {
    return new BookListController();
  }
}
