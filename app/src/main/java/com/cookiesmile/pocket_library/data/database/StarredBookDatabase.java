package com.cookiesmile.pocket_library.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = StarredBook.class, version = 1)
public abstract class StarredBookDatabase extends RoomDatabase {

  public abstract StarredBookDao starredBookDao();
}
