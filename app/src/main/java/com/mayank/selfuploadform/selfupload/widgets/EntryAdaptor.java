package com.mayank.selfuploadform.selfupload.widgets;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class EntryAdaptor<T> extends BaseAdapter {
  public static final int FIRST = 0;
  private final List<T> entries;
  private final EntryAdaptorCallback<T> listener;
  private CharSequence defaultEntry;
  private boolean hasDefault;


  public EntryAdaptor(EntryAdaptorCallback<T> listener) {
    entries = new ArrayList<>();
    this.listener = listener;
  }

  public void addEntries(List<T> values) {
    if (null == values || values.isEmpty()) {
      return;
    }
    entries.addAll(values);
    notifyDataSetChanged();
  }

  public void setDefaultEntry(CharSequence entry) {
    this.defaultEntry = entry;
    this.hasDefault = !TextUtils.isEmpty(defaultEntry);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return entries.size() + (hasDefault ? 1 : 0);
  }

  @Override
  public Object getItem(int position) {
    return entries.get(getPosition(position));
  }

  public T getEntry(int position) {
    return entries.get(getPosition(position));
  }

  @Override
  public long getItemId(int position) {
    return getPosition(position);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = listener.createView();
    }
    if (hasDefault && FIRST == position) {
      listener.updateDefaultView(convertView, defaultEntry);
    } else {
      listener.updateView(convertView, entries.get(getPosition(position)));
    }
    return convertView;
  }

  @Override
  public boolean isEmpty() {
    if (hasDefault) {
      return entries.isEmpty();
    }
    return super.isEmpty();
  }

  private int getPosition(int position) {
    if (hasDefault) {
      return position - 1;
    }
    return position;
  }

  public interface EntryAdaptorCallback<E> {
    View createView();

    void updateView(View convertView, E entry);

    void updateDefaultView(View convertView, CharSequence defaultString);
  }
}