package com.mayank.selfuploadform.selfupload.base;

import com.mayank.selfuploadform.selfupload.widgets.BaseField;

public class BaseSelfUploadEntry implements BaseField.BaseEntry {
  private CharSequence text;
  private CharSequence id;

  public BaseSelfUploadEntry(CharSequence text, CharSequence id) {
    this.text = text;
    this.id = id;
  }

  @Override
  public CharSequence getEntryText() {
    return text;
  }

  public CharSequence getId() {
    return id;
  }

  @Override
  public String toString() {
    return "BaseSelfUploadEntry{" +
            "text=" + text +
            ", id=" + id +
            '}';
  }
}
