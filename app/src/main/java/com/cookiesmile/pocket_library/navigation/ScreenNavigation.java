package com.cookiesmile.pocket_library.navigation;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.cookiesmile.pocket_library.di.ActivityScope;
import com.cookiesmile.pocket_library.screen.book_detail.BookDetailController;

import javax.inject.Inject;

@ActivityScope
public class ScreenNavigation {

  private Router router;

  @Inject
  ScreenNavigation() {

  }

  public void initWithRouter(Router router, Controller rootScreen) {
    this.router = router;
    if (!router.hasRootController()) {
      router.setRoot(RouterTransaction.with(rootScreen));
    }
  }

  public boolean pop() {
    return router != null && router.handleBack();
  }

  public void goToBookDetail(long id) {
    if (router != null) {
      router.pushController(RouterTransaction.with(
          BookDetailController.newInstance(id))
          .pushChangeHandler(new FadeChangeHandler())
          .popChangeHandler(new FadeChangeHandler()));
    }
  }

  public void clear() {
    router = null;
  }
}
