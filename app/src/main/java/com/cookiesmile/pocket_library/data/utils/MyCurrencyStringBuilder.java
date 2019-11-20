package com.cookiesmile.pocket_library.data.utils;

public class MyCurrencyStringBuilder {

  public static String create(int price, String currencyCode) {
    switch (currencyCode.trim().toUpperCase()) {
      case "EUR":
        return String.format("%d €", price);
      case "GBP":
        return String.format("%d £", price);
      case "USD":
        return String.format("$ %d", price);
    }
    return String.format("%d ?", price);
  }
}
