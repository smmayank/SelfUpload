package com.mayank.selfuploadform.selfupload.base;

import com.mayank.selfuploadform.selfupload.widgets.BaseField;

public class BaseSelfUploadEntry<V> implements BaseField.BaseEntry {
  private CharSequence text;
  private V value;

  public BaseSelfUploadEntry(CharSequence text, V id) {
    this.text = text;
    this.value = id;
  }

  @Override
  public CharSequence getEntryText() {
    return text;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "BaseSelfUploadEntry{" +
            "text=" + text +
            ", value=" + value +
            '}';
  }
}
