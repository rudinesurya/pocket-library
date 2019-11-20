package com.cookiesmile.pocket_library.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class BookDetail {

  public static JsonAdapter<BookDetail> jsonAdapter(Moshi moshi) {
    return new AutoValue_BookDetail.MoshiJsonAdapter(moshi);
  }

  public static Builder builder() {
    return new AutoValue_BookDetail.Builder();
  }

  public abstract long id();

  public abstract String title();

  public abstract String isbn();

  public abstract String author();

  public abstract String description();

  public abstract int price();

  public abstract String currencyCode();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder id(long value);

    public abstract Builder title(String value);

    public abstract Builder isbn(String value);

    public abstract Builder author(String value);

    public abstract Builder description(String value);

    public abstract Builder price(int value);

    public abstract Builder currencyCode(String value);

    public abstract BookDetail build();
  }
}
