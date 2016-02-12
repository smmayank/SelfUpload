package com.mayank.selfuploadform.selfupload.widgets;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntryAdaptor<T> extends BaseAdapter {


  private static final int FIRST = 0;
  private final List<T> entries;
  private final EntryAdaptorCallback<T> listener;
  private T defaultEntry;


  public EntryAdaptor(EntryAdaptorCallback<T> listener) {
    entries = new ArrayList<>();
    this.listener = listener;
  }

  public void setDefaultEntry(T entry) {
    if (null != defaultEntry) {
      entries.remove(defaultEntry);
    }
    this.defaultEntry = entry;
    if (null != defaultEntry) {
      entries.add(FIRST, defaultEntry);
    }
    notifyDataSetChanged();
  }

  public void addEntries(T[] values) {
    if (null == values || 0 == values.length) {
      return;
    }
    Collections.addAll(entries, values);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return entries.size();
  }

  @Override
  public Object getItem(int position) {
    return entries.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = listener.createView();
    }
    listener.updateView(convertView, entries.get(position));
    return convertView;
  }

  public interface EntryAdaptorCallback<E> {
    View createView();

    void updateView(View convertView, E entry);
  }
}