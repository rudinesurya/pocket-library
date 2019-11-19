package com.cookiesmile.pocket_library.data.model;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class BookListData {

  public static Builder builder() {
    return new AutoValue_BookListData.Builder();
  }

  public abstract List<Book> data();

  public abstract String source();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder data(List<Book> value);

    public abstract Builder source(String value);

    public abstract BookListData build();
  }
}
