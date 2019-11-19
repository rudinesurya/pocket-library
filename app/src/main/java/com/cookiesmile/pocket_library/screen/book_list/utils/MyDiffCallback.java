package com.cookiesmile.pocket_library.screen.book_list.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.cookiesmile.pocket_library.data.model.Book;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback {

  private final List<Book> oldList;
  private final List<Book> newList;

  public MyDiffCallback(List<Book> oldList, List<Book> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override
  public int getOldListSize() {
    return oldList.size();
  }

  @Override
  public int getNewListSize() {
    return newList.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return oldList.get(oldItemPosition).id() == newList.get(newItemPosition).id();
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
  }
}