package com.cookiesmile.pocket_library.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Book {

  public static JsonAdapter<Book> jsonAdapter(Moshi moshi) {
    return new AutoValue_Book.MoshiJsonAdapter(moshi);
  }

  public static Builder builder() {
    return new AutoValue_Book.Builder();
  }

  public abstract long id();

  public abstract String title();

  public abstract String isbn();

  public abstract String author();

  public abstract int price();

  public abstract String currencyCode();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder id(long value);

    public abstract Builder title(String value);

    public abstract Builder isbn(String value);

    public abstract Builder author(String value);

    public abstract Builder price(int value);

    public abstract Builder currencyCode(String value);

    public abstract Book build();
  }
}
