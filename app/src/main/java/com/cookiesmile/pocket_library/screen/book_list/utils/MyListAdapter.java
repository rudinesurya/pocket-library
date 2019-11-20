package com.cookiesmile.pocket_library.screen.book_list.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cookiesmile.pocket_library.R;
import com.cookiesmile.pocket_library.data.model.Book;
import com.cookiesmile.pocket_library.data.utils.MyCurrencyStringBuilder;
import com.cookiesmile.pocket_library.screen.book_list.utils.MyListAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyListAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final ItemClickListener listener;
  private final List<Book> data = new ArrayList<>();

  public MyListAdapter(ItemClickListener listener) {
    this.listener = listener;
    setHasStableIds(true);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater
        .from(parent.getContext()).inflate(R.layout.item_book, parent, false);
    return new ViewHolder(itemView, listener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(data.get(position));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  @Override
  public long getItemId(int position) {
    return data.get(position).id();
  }

  public void setData(List<Book> newData) {
    if (newData != null) {
      DiffUtil.DiffResult diffResult = DiffUtil
          .calculateDiff(new MyDiffCallback(data, newData));
      data.clear();
      data.addAll(newData);
      diffResult.dispatchUpdatesTo(this);
    } else {
      data.clear();
      notifyDataSetChanged();
    }
  }

  public interface ItemClickListener {

    void onItemClickListener(Book book);
  }

  static final class ViewHolder extends RecyclerView.ViewHolder {

    Context context;
    @BindView(R.id.tv_title)
    TextView titleText;
    @BindView(R.id.tv_author)
    TextView authorText;
    @BindView(R.id.tv_price)
    TextView priceText;

    private Book book;

    ViewHolder(@NonNull View itemView, ItemClickListener listener) {
      super(itemView);
      context = itemView.getContext();
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(v -> {
        if (book != null) {
          listener.onItemClickListener(book);
        }
      });
    }

    void bind(Book book) {
      this.book = book;
      titleText.setText(book.title());
      authorText.setText(book.author());
      priceText.setText(MyCurrencyStringBuilder.create(book.price(), book.currencyCode()));
    }
  }
}