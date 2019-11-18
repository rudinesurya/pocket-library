package com.cookiesmile.pocket_library.di;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;
import com.cookiesmile.pocket_library.base.BaseActivity;
import com.cookiesmile.pocket_library.base.BaseController;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;
import dagger.android.AndroidInjector.Factory;

@ActivityScope
public class ScreenInjector {

  private final Map<Class<? extends Controller>, Provider<Factory<? extends Controller>>> screenInjectors;
  private final Map<String, AndroidInjector<Controller>> cache = new HashMap<>();

  @Inject
  ScreenInjector(
      Map<Class<? extends Controller>, Provider<AndroidInjector.Factory<? extends Controller>>> screenInjectors) {
    this.screenInjectors = screenInjectors;
  }

  static ScreenInjector get(Activity activity) {
    if (!(activity instanceof BaseActivity)) {
      throw new IllegalArgumentException("Controller must be hosted by BaseActivity");
    }
    return ((BaseActivity) activity).getScreenInjector();
  }

  void inject(Controller controller) {
    if (!(controller instanceof BaseController)) {
      throw new IllegalArgumentException("Controller must extend BaseController");
    }

    String instanceId = controller.getInstanceId();
    if (cache.containsKey(instanceId)) {
      cache.get(instanceId).inject(controller);
      return;
    }

    //noinspection unchecked
    AndroidInjector.Factory<Controller> injectorFactory =
        (AndroidInjector.Factory<Controller>) screenInjectors.get(controller.getClass()).get();
    AndroidInjector<Controller> injector = injectorFactory.create(controller);
    cache.put(instanceId, injector);
    injector.inject(controller);
  }

  void clear(Controller controller) {
    cache.remove(controller.getInstanceId());
  }
}
