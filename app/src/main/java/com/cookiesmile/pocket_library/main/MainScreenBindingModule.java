package com.cookiesmile.pocket_library.main;

import com.bluelinelabs.conductor.Controller;
import com.cookiesmile.pocket_library.di.ControllerKey;
import com.cookiesmile.pocket_library.screen.book_detail.BookDetailComponent;
import com.cookiesmile.pocket_library.screen.book_detail.BookDetailController;
import com.cookiesmile.pocket_library.screen.book_list.BookListComponent;
import com.cookiesmile.pocket_library.screen.book_list.BookListController;
import com.cookiesmile.pocket_library.screen.settings.SettingsComponent;
import com.cookiesmile.pocket_library.screen.settings.SettingsController;
import com.cookiesmile.pocket_library.screen.starred.StarredBookListComponent;
import com.cookiesmile.pocket_library.screen.starred.StarredBookListController;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
    BookListComponent.class,
    StarredBookListComponent.class,
    BookDetailComponent.class,
    SettingsComponent.class,
})
abstract class MainScreenBindingModule {

  @Binds
  @IntoMap
  @ControllerKey(BookListController.class)
  abstract AndroidInjector.Factory<? extends Controller> bindBookListInjector(
      BookListComponent.Builder builder);

  @Binds
  @IntoMap
  @ControllerKey(StarredBookListController.class)
  abstract AndroidInjector.Factory<? extends Controller> bindBookListStarredInjector(
      StarredBookListComponent.Builder builder);

  @Binds
  @IntoMap
  @ControllerKey(BookDetailController.class)
  abstract AndroidInjector.Factory<? extends Controller> bindBookDetailInjector(
      BookDetailComponent.Builder builder);

  @Binds
  @IntoMap
  @ControllerKey(SettingsController.class)
  abstract AndroidInjector.Factory<? extends Controller> bindSettingsInjector(
      SettingsComponent.Builder builder);
}