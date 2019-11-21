package com.cookiesmile.pocket_library.base;


import com.cookiesmile.pocket_library.data.api.BookServerApiServiceModule;
import com.cookiesmile.pocket_library.data.database.DatabaseModule;
import com.cookiesmile.pocket_library.data.networking.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    ActivityBindingModule.class,
    ServiceModule.class,
    BookServerApiServiceModule.class,
    DatabaseModule.class,
})
public interface ApplicationComponent {

  void inject(MyApplication myApplication);
}
