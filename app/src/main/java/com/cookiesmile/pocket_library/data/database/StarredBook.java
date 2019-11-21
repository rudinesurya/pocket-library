package com.cookiesmile.pocket_library.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StarredBook {

  @PrimaryKey
  private final long id;

  public StarredBook(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }
}
