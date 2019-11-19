package com.cookiesmile.pocket_library.main;

import com.bluelinelabs.conductor.Controller;
import com.cookiesmile.pocket_library.di.ControllerKey;
import com.cookiesmile.pocket_library.screen.book_list.BookListComponent;
import com.cookiesmile.pocket_library.screen.book_list.BookListController;
import com.cookiesmile.pocket_library.screen.main.MainController;
import com.cookiesmile.pocket_library.screen.main.MainControllerComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
    MainControllerComponent.class,
    BookListComponent.class,
})
abstract class MainScreenBindingModule {

  @Binds
  @IntoMap
  @ControllerKey(MainController.class)
  abstract AndroidInjector.Factory<? extends Controller> bindMainControllerInjector(
      MainControllerComponent.Builder builder);

  @Binds
  @IntoMap
  @ControllerKey(BookListController.class)
  abstract AndroidInjector.Factory<? extends Controller> bindBookListInjector(
      BookListComponent.Builder builder);

//  @Binds
//  @IntoMap
//  @ControllerKey(BookDetailController.class)
//  abstract AndroidInjector.Factory<? extends Controller> bindBookDetailInjector(
//      BookDetailComponent.Builder builder);
}