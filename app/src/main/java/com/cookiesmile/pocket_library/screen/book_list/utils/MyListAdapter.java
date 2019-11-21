package com.cookiesmile.pocket_library.screen.book_list.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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

    void onStarItemClickListener(Book book);

    void onUnStarItemClickListener(Book book);
  }

  static final class ViewHolder extends RecyclerView.ViewHolder implements
      PopupMenu.OnMenuItemClickListener {

    Context context;
    @BindView(R.id.tv_title)
    TextView titleText;
    @BindView(R.id.tv_author)
    TextView authorText;
    @BindView(R.id.tv_price)
    TextView priceText;
    @BindView(R.id.btn_menu)
    ImageButton menuBtn;

    private Book book;
    private ItemClickListener listener;

    ViewHolder(@NonNull View itemView, ItemClickListener listener) {
      super(itemView);
      context = itemView.getContext();
      this.listener = listener;
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(v -> {
        if (book != null) {
          listener.onItemClickListener(book);
        }
      });
      menuBtn.setOnClickListener(v -> {
        if (book != null) {
          showPopupMenu(menuBtn, book);
        }
      });
    }

    private void showPopupMenu(View view, Book book) {
      // inflate menu
      PopupMenu popup = new PopupMenu(context, view);
      MenuInflater inflater = popup.getMenuInflater();
      inflater.inflate(R.menu.popup_menu, popup.getMenu());
      popup.setOnMenuItemClickListener(this);
      popup.show();
    }

    void bind(Book book) {
      this.book = book;
      titleText.setText(book.title());
      authorText.setText(book.author());
      priceText.setText(MyCurrencyStringBuilder.create(book.price(), book.currencyCode()));
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
      switch (item.getItemId()) {
        case R.id.star_book:
          listener.onStarItemClickListener(book);
          return true;

        case R.id.not_interested:
          listener.onUnStarItemClickListener(book);
          return true;
      }
      return false;
    }
  }
}