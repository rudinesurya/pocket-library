package com.cookiesmile.pocket_library.base;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.di.Injector;
import com.cookiesmile.pocket_library.di.ScreenInjector;
import com.cookiesmile.pocket_library.navigation.ScreenNavigation;

import java.util.UUID;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

  private static final String INSTANCE_ID_KEY = "instance_id";

  @Inject
  ScreenInjector screenInjector;
  @Inject
  ScreenNavigation screenNavigation;

  private String instanceId;
  private Router router;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
    } else {
      instanceId = UUID.randomUUID().toString();
    }
    Injector.inject(this);
    super.onCreate(savedInstanceState);

    setContentView(layoutRes());
    ViewGroup screenContainer = findViewById(R.id.screen_container);
    if (screenContainer == null) {
      throw new NullPointerException("Activity must have a view with id: screen_container");
    }

    router = Conductor.attachRouter(this, screenContainer, savedInstanceState);
    screenNavigation.initWithRouter(router, initialScreen());
    monitorBackStack();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(INSTANCE_ID_KEY, instanceId);
  }

  @Override
  public void onBackPressed() {
    if (!screenNavigation.pop()) {
      super.onBackPressed();
    }
  }

  @LayoutRes
  protected abstract int layoutRes();

  protected abstract Controller initialScreen();

  public String getInstanceId() {
    return instanceId;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    screenNavigation.clear();
    if (isFinishing()) {
      Injector.clearComponent(this);
    }
  }

  public ScreenInjector getScreenInjector() {
    return screenInjector;
  }

  private void monitorBackStack() {
    router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
      @Override
      public void onChangeStarted(
          @Nullable Controller to,
          @Nullable Controller from,
          boolean isPush,
          @NonNull ViewGroup container,
          @NonNull ControllerChangeHandler handler) {

      }

      @Override
      public void onChangeCompleted(
          @Nullable Controller to,
          @Nullable Controller from,
          boolean isPush,
          @NonNull ViewGroup container,
          @NonNull ControllerChangeHandler handler) {
        if (!isPush && from != null) {
          Injector.clearComponent(from);
        }
      }
    });
  }
}
