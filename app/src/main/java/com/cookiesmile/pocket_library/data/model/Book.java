package com.cookiesmile.pocket_library.data.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Book {

  public static Builder builder() {
    return new AutoValue_Book.Builder();
  }

  public abstract long id();

  public abstract String title();

  public abstract String isbn();

  public abstract String author();

  public abstract String description();

  public abstract int price();

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder id(long value);

    public abstract Builder title(String value);

    public abstract Builder isbn(String value);

    public abstract Builder author(String value);

    public abstract Builder description(String value);

    public abstract Builder price(int value);

    public abstract Book build();
  }
}
