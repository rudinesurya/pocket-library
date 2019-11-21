package com.cookiesmile.pocket_library.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StarredBookDao {

  @Query("select * from starredbook")
  Flowable<List<StarredBook>> getStarredBooks();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void addStarred(StarredBook book);

  @Delete
  void deleteStarred(StarredBook book);
}
