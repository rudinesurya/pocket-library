package com.cookiesmile.pocket_library.screen.settings;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.base.BaseController;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsController extends BaseController {

  @Inject
  ScreenNavigation screenNavigation;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.btn_clear_cache)
  Button clearCacheBtn;

  @Override
  protected int layoutRes() {
    return R.layout.screen_settings;
  }

  @Override
  protected void onViewBound(View view) {
    toolbar.setTitle("Settings");
    toolbar.setNavigationIcon(R.drawable.ic_back);
    toolbar.setNavigationOnClickListener(v -> screenNavigation.pop());

    clearCacheBtn.setOnClickListener(__ -> {
      // Clear cache
      deleteCache(getApplicationContext());
      Toast.makeText(getApplicationContext(), "Cache Cleared", Toast.LENGTH_LONG).show();
    });
  }

  void deleteCache(Context context) {
    try {
      File dir = context.getCacheDir();
      deleteDir(dir);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
      return dir.delete();
    } else if (dir != null && dir.isFile()) {
      return dir.delete();
    } else {
      return false;
    }
  }
}
