package com.cookiesmile.pocket_library.data.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BookDetailData {

  public static Builder builder() {
    return new AutoValue_BookDetailData.Builder();
  }

  public abstract BookDetail data();

  public abstract String source();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder data(BookDetail value);

    public abstract Builder source(String value);

    public abstract BookDetailData build();
  }
}
